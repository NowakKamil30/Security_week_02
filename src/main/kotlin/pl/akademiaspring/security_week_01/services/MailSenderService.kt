package pl.akademiaspring.security_week_01.services

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import javax.mail.internet.MimeMessage

@Service
class MailSenderService(private val javaMailSender: JavaMailSender) {

    fun sendMail(
            to: String,
            subject: String,
            text: String,
            isHtmlContent: Boolean
    ) {
        val mimeMessage: MimeMessage = javaMailSender.createMimeMessage()
        val mimeMessageHelper: MimeMessageHelper = MimeMessageHelper(mimeMessage, true)
        mimeMessageHelper.setTo(to)
        mimeMessageHelper.setSubject(subject)
        mimeMessageHelper.setText(text, isHtmlContent)
        javaMailSender.send(mimeMessage)
    }
}