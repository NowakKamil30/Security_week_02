package pl.akademiaspring.security_week_01.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.akademiaspring.security_week_01.models.VerificationToken

@Repository
interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {

    fun findByToken(token: String): VerificationToken
}