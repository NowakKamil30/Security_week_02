package pl.akademiaspring.security_week_01.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.akademiaspring.security_week_01.services.HelloService
import java.security.Principal

@RestController
@RequestMapping("/hello")
class HelloController(val helloService: HelloService) {

    @GetMapping("/forAdmin")
    fun helloAdmin(principal: Principal): String {
        return helloService.helloAdmin(principal.name)
    }

    @GetMapping("/forUser")
    fun helloUser(principal: Principal): String {
        return helloService.helloUser(principal.name)
    }

    @GetMapping("/forUnknown")
    fun helloUnKnow(principal: Principal?): String {
        return helloService.helloUnKnow(principal?.name)
    }

    @GetMapping("/bye")
    fun bye(): String {
        return helloService.bye()
    }
}