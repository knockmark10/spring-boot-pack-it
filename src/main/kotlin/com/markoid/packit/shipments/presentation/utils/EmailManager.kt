package com.markoid.packit.shipments.presentation.utils

import org.slf4j.LoggerFactory
import org.springframework.core.io.ByteArrayResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.web.multipart.MultipartFile
import java.util.concurrent.CompletableFuture

open class EmailManager {

    private val logger = LoggerFactory.getLogger(EmailManager::class.java)

    /**
     * Send email in an async way to prevent delaying the response after having uploaded the file.
     */
    @Async("threadPoolTaskExecutor")
    open fun sendEmail(
        emailSender: JavaMailSender,
        to: String,
        subject: String = "",
        emailContent: String,
        from: String? = null,
        fileName: String? = null,
        file: MultipartFile? = null
    ): CompletableFuture<Boolean> = try {
        // Create Mime Message
        val message = emailSender.createMimeMessage()

        // Create multipart helper
        val helper = MimeMessageHelper(message, true)

        // Set email to send the email to
        helper.setTo(to)

        // Set subject to email
        helper.setSubject(subject)

        // Set text to email
        helper.setText(emailContent)

        // Set sender mail (if provided)
        from?.let { helper.setFrom(it) }

        // Set the attachment (if provided)
        file?.let {
            helper.addAttachment(fileName ?: it.originalFilename ?: it.name, convertFileToByteArray(it))
        }

        // Send email
        emailSender.send(message)

        // Log successful sending
        this.logger.info("Email has been sent successfully.")

        // Return completion signal as successful
        CompletableFuture.completedFuture(true)
    } catch (exception: Throwable) {
        // Log the encountered error for debugging purposes
        this.logger.error(exception.localizedMessage, exception)
        // Return signal as unsuccessful
        CompletableFuture.completedFuture(false)
    }

    private fun convertFileToByteArray(file: MultipartFile): ByteArrayResource =
        ByteArrayResource(file.bytes)

}