package com.architect.neuralKmp.translation

import com.architect.kmpessentials.logging.KmpLogging
import com.architect.kmpessentials.toast.KmpToast
import com.architect.neuralKmp.typealiases.DefaultActionWithStringParam
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

            Translation.getClient(options).translate(translatingText).addOnSuccessListener {
                action(it)
            }
                .addOnFailureListener {
                    KmpLogging.writeError("KMP-ML-KIT", it.stackTraceToString())
                    KmpToast.showToastShort("mmm...something went wrong")
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
                    KmpLogging.writeError("KMP-ML-KIT", it.stackTraceToString())
                    KmpToast.showToastShort("mmm...something went wrong")
                }
        }
    }
}