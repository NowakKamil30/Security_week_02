package pl.akademiaspring.security_week_01.services

import org.springframework.stereotype.Service
import pl.akademiaspring.security_week_01.models.VerificationToken
import pl.akademiaspring.security_week_01.repositories.VerificationTokenRepository

@Service
class VerificationTokenService(private val verificationTokenRepository: VerificationTokenRepository) {

    fun findByToken(token: String): VerificationToken = verificationTokenRepository.findByToken(token)
    fun save(verificationToken: VerificationToken) = verificationTokenRepository.save(verificationToken)
    fun delete(verificationToken: VerificationToken) = verificationTokenRepository.delete(verificationToken)
}