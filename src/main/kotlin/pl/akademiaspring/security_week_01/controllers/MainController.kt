package pl.akademiaspring.security_week_01.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import pl.akademiaspring.security_week_01.Roles
import pl.akademiaspring.security_week_01.exceptions.UserExistsException
import pl.akademiaspring.security_week_01.models.AppUser
import pl.akademiaspring.security_week_01.services.AppUserService
import pl.akademiaspring.security_week_01.services.VerificationTokenService
import javax.servlet.http.HttpServletRequest

@Controller
class MainController(
        private val appUserService: AppUserService,
        private val verificationTokenService: VerificationTokenService
) {
    @GetMapping("/login")
    fun getLoginPage(): String = "login"

    @GetMapping("/sign-up")
    fun getSignUpPage(): ModelAndView = ModelAndView("registration", "user", AppUser())

    @ExceptionHandler(UserExistsException::class)
    @GetMapping("/error")
    fun getErrorPage(): String = "error"


    @PostMapping("/register")
    fun signUp(appUser: AppUser, httpServletRequest: HttpServletRequest): String {
        appUserService.addAppUser(appUser, httpServletRequest)
        return "redirect:/login"
    }
    @GetMapping("/verify-token")
    fun verifyToken(@RequestParam token: String): String {
        val verificationToken = verificationTokenService.findByToken(token)
        if (verificationToken.isTokenForAdmin) {
            verificationToken.appUser.role = Roles.ADMIN
        } else {
            verificationToken.appUser.isEnable = true
        }
        appUserService.save(verificationToken.appUser)
        verificationTokenService.delete(verificationToken)
        return "redirect:/login"
    }
}