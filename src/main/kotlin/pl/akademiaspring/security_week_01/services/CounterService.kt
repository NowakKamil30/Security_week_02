package pl.akademiaspring.security_week_01.services

import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.stereotype.Service
import pl.akademiaspring.security_week_01.repositories.AppUserRepository

@Service
class CounterService(private val appUserRepository: AppUserRepository) {

    fun getCounterByUser(name: String): Int? {
        return appUserRepository.findByUsername(name)?.numberOfLogin
    }

    @EventListener
    fun authentication(authenticationSuccessEvent: AuthenticationSuccessEvent) {
        counter(authenticationSuccessEvent.authentication.name)
    }

    private fun counter(name: String) {
        appUserRepository.findByUsername(name)?.let {
           it.numberOfLogin++
            appUserRepository.save(it)
       }

    }
}