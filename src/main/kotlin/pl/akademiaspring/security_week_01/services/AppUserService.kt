package pl.akademiaspring.security_week_01.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.akademiaspring.security_week_01.Roles
import pl.akademiaspring.security_week_01.exceptions.UserExistsException
import pl.akademiaspring.security_week_01.models.AppUser
import pl.akademiaspring.security_week_01.models.VerificationToken
import pl.akademiaspring.security_week_01.repositories.AppUserRepository
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class AppUserService(
        private val appUserRepository: AppUserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val mailSenderService: MailSenderService,
        private val verificationTokenService: VerificationTokenService
) {
    @Value("\${admin.email}")
    private lateinit var adminEmail: String;
    fun findAppUserByUsername(username: String): AppUser? = appUserRepository.findByUsername(username)

    fun save(appUser: AppUser): AppUser = appUserRepository.save(appUser)

    fun addAppUser(appUser: AppUser, httpServletRequest: HttpServletRequest?): AppUser {
        val checkAppUser = findAppUserByUsername(appUser.username)
        if(checkAppUser != null) {
            throw UserExistsException()
        }
        appUser.password = passwordEncoder.encode(appUser.password)
        var saveAppUser: AppUser? = null
        if (Roles.ADMIN == appUser.role) {
            appUser.role = Roles.USER
            saveAppUser = appUserRepository.save(appUser)
            val verificationToken = generateToken(saveAppUser)
            verificationToken.token + "admin"
            verificationToken.isTokenForAdmin = true
            verificationTokenService.save(verificationToken)
            val url = prepareUrl(httpServletRequest)
            url.append(verificationToken.token)
            mailSenderService.sendMail(
                    adminEmail,
                    "Verification Token",
                    url.toString(),
                    false)
        }
        if (saveAppUser == null) {
            saveAppUser = appUserRepository.save(appUser)
        }
        val verificationToken = generateToken(saveAppUser)
        verificationTokenService.save(verificationToken)
        val url = prepareUrl(httpServletRequest)
        url.append(verificationToken.token)
        mailSenderService.sendMail(
                saveAppUser.username,
                "Verification Token",
                url.toString(),
                false)
        return saveAppUser
    }

    private fun generateToken(appUser: AppUser): VerificationToken {
        val verificationToken = VerificationToken()
        verificationToken.token = UUID.randomUUID().toString() + appUser.id
        verificationToken.appUser = appUser
        return verificationToken
    }

    private fun prepareUrl(httpServletRequest: HttpServletRequest?): StringBuilder {
        val url: StringBuilder = java.lang.StringBuilder("http://")
        url.apply {
            append(httpServletRequest?.serverName)
            append(":")
            append(httpServletRequest?.serverPort)
            append(httpServletRequest?.contextPath)
            append("/verify-token?token=")
        }
        return url
    }
}