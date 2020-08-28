package pl.akademiaspring.security_week_01.bootstrap

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import pl.akademiaspring.security_week_01.Roles
import pl.akademiaspring.security_week_01.models.AppUser
import pl.akademiaspring.security_week_01.services.AppUserService

@Component
class UserLoader(val appUserService: AppUserService) : CommandLineRunner {

    @Value("\${admin.email}")
    private lateinit var email: String;
    @Value("\${admin.password}")
    private lateinit var password: String
    override fun run(vararg args: String?) {
        val appUser = AppUser()
        appUser.password = password
        appUser.username = email
        appUser.numberOfLogin = 0
        appUser.role = Roles.ADMIN
        appUser.isEnable = true
        appUserService.save(appUser)
    }
}