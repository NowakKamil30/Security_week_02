package pl.akademiaspring.security_week_01.services

import org.springframework.stereotype.Service

@Service
class HelloService(val counterService: CounterService) {

    fun helloAdmin(name: String): String {
        return "Cześć admin $name (${counterService.getCounterByUser(name)})"
    }

    fun helloUser(name: String): String {
        return "Cześć user $name (${counterService.getCounterByUser(name)})"
    }

    fun helloUnKnow(name: String?): String {
        return "Cześć ${if(name.isNullOrBlank()) "nieznajomy" else name}"
    }
    fun bye(): String {
        return "Papa"
    }
}