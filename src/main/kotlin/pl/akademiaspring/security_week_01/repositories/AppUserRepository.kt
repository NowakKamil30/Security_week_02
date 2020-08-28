package pl.akademiaspring.security_week_01.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.akademiaspring.security_week_01.models.AppUser

@Repository
interface AppUserRepository : CrudRepository<AppUser, Long> {
    fun findByUsername(username: String): AppUser?
}