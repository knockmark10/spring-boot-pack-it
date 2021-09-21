package com.markoid.packit.shipments.domain.usecases

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Failure
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Success
import com.markoid.packit.core.presentation.handlers.MessageDictionary.MISSING_PARAMETERS
import com.markoid.packit.core.presentation.handlers.MessageDictionary.SHIPMENT_CREATION_SUCCESSFUL
import com.markoid.packit.shipments.domain.usecases.requests.SendMailDto
import com.markoid.packit.shipments.presentation.utils.EmailManager
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.multipart.MultipartFile

/**
 * Marked as open for async purposes.
 */
class SendShipmentMailUseCase(
    private val emailSender: JavaMailSender,
    private val emailManager: EmailManager
) : AbstractUseCase<ApiResult, SendMailDto>() {

    override fun onHandleValidations(params: SendMailDto): ValidationStatus = when {
        params.file == null || params.file.isEmpty || params.email.isNullOrEmpty() -> Failure(MISSING_PARAMETERS)
        isFileValid(params.file).not() -> Failure(MISSING_PARAMETERS)
        else -> Success
    }

    private fun isFileValid(file: MultipartFile): Boolean = try {
        val reader = PdfReader(file.inputStream)
        val textFromPageOne = PdfTextExtractor.getTextFromPage(reader, 1)
        textFromPageOne.isNullOrBlank().not()
    } catch (exception: Exception) {
        false
    }

    override fun onExecuteTask(params: SendMailDto): ApiResult {
        // Send email async
        this.emailManager.sendEmail(
            emailSender = emailSender,
            from = "noreply@packitserver.com",
            subject = "Pack It - Shipments Summary",
            to = params.email!!,
            emailContent = "In the attachments you can find the summary of your shipment.",
            fileName = params.file!!.originalFilename ?: "Shipment",
            file = params.file
        )

        // Return success message
        return okMessage(SHIPMENT_CREATION_SUCCESSFUL)
    }

}