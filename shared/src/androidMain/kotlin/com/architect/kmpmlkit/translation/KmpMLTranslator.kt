package com.architect.neuralKmp.translation

import com.architect.kmpessentials.logging.KmpLogging
import com.architect.kmpessentials.toast.KmpToast
import com.architect.kmpmlkit.extensions.KmpMlLogger
import com.architect.neuralKmp.typealiases.DefaultActionWithStringParam
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

actual class KmpMLTranslator {
    actual companion object {
        private fun prepareTranslator(
            translatingText: String,
            targetLanguage: String,
            fromLanguage: String,
            action: DefaultActionWithStringParam
        ) {
            // Create an English-German translator:
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(fromLanguage)
                .setTargetLanguage(targetLanguage)
                .build()

            val client = Translation.getClient(options)
            client.downloadModelIfNeeded(DownloadConditions.Builder().requireWifi().build())
                .addOnSuccessListener { r ->
                    KmpLogging.writeInfo(
                        "KMP_ML_TRANSLATOR",
                        "Successful Download Translator Model"
                    )
                    client.translate(translatingText).addOnSuccessListener {
                        action(it)
                    }.addOnFailureListener { q ->
                        KmpMlLogger.logErrorWithStackTrace(q.stackTraceToString())
                    }
                }.addOnFailureListener {
                    KmpMlLogger.logErrorWithStackTrace(it.stackTraceToString())
                }
        }

        actual fun translateTextToTargetLanguage(
            translatingText: String,
            targetLanguage: String,
            fromLanguage: String,
            action: DefaultActionWithStringParam
        ) {
            prepareTranslator(
                translatingText,
                targetLanguage,
                fromLanguage,
                action
            )
        }

        actual fun autoTranslateTextToTargetLanguage(
            translatingText: String,
            targetLanguage: String,
            action: DefaultActionWithStringParam
        ) {
            val languageIdentifier = LanguageIdentification.getClient()
            languageIdentifier.identifyLanguage(translatingText)
                .addOnSuccessListener { languageCode ->
                    prepareTranslator(
                        translatingText,
                        targetLanguage,
                        languageCode,
                        action
                    )
                }
                .addOnFailureListener {
                    KmpMlLogger.logErrorWithStackTrace(it.stackTraceToString())
                }
        }
    }
}