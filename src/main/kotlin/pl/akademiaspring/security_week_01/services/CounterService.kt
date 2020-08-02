package pl.akademiaspring.security_week_01.services

import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.stereotype.Service

@Service
class CounterService(val hashMap: HashMap<String, Int> = HashMap()) {
    init {
        hashMap.apply {
            put("user", 0)
            put("Kamil", 0)
        }
    }

    fun getCounterByUser(name: String): Int? {
        return hashMap[name]
    }

    @EventListener
    fun authentication(authenticationSuccessEvent: AuthenticationSuccessEvent) {
        counter(authenticationSuccessEvent.authentication.name)
    }

    private fun counter(name: String) {
        hashMap[name]?.plus(1)?.let { hashMap.put(name, it) }
    }
}