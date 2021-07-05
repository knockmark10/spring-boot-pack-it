package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Failure
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Success
import com.markoid.packit.core.presentation.handlers.MessageDictionary.MISSING_PARAMETERS
import com.markoid.packit.core.presentation.handlers.MessageDictionary.SHIPMENT_CREATION_SUCCESSFUL
import com.markoid.packit.shipments.domain.usecases.requests.SendMailDto
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper

class SendShipmentMailUseCase(
    private val mailSender: JavaMailSender
) : AbstractUseCase<ApiResult, SendMailDto>() {

    override fun onHandleValidations(params: SendMailDto): ValidationStatus = when {
        params.file == null || params.file.isEmpty || params.email.isNullOrEmpty() -> Failure(MISSING_PARAMETERS)
        else -> Success
    }

    override fun onExecuteTask(params: SendMailDto): ApiResult {
        // Create Mime Message
        val message = mailSender.createMimeMessage()

        // Create multipart helper
        val helper = MimeMessageHelper(message, true)

        // Set email to send the email to
        helper.setTo(params.email!!)

        // Set sender mail
        helper.setFrom("noreply@packit.com")

        // Set subject to email
        helper.setSubject("Pack It - Shipments Summary")

        // Set text to email
        helper.setText("In the attachments you can find the summary of your shipment.")

        // Set the attachment
        helper.addAttachment(params.file!!.originalFilename ?: "Shipment", params.file)

        // Send email with attachment
        this.mailSender.send(message)

        // Return success message
        return buildSuccessfulMessage(SHIPMENT_CREATION_SUCCESSFUL)
    }

}