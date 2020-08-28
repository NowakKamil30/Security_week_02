package pl.akademiaspring.security_week_01.services

import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import pl.akademiaspring.security_week_01.models.AppUser
import pl.akademiaspring.security_week_01.repositories.AppUserRepository

@Service
@Primary
class UserDetailsServiceImp(
        private val appUserRepository: AppUserRepository
) : UserDetailsService {
    override fun loadUserByUsername(p0: String?): AppUser? {
        return p0?.let { appUserRepository.findByUsername(it) }
    }
}