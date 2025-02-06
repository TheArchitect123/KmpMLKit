package com.architect.neuralKmp.languageIdentification

import com.architect.kmpessentials.logging.KmpLogging
import com.architect.kmpessentials.toast.KmpToast
import com.architect.kmpmlkit.extensions.KmpMlLogger
import com.architect.neuralKmp.typealiases.DefaultActionWithStringParam
import com.google.mlkit.nl.languageid.LanguageIdentification

actual class KmpMLLanguageDetection {
    actual companion object {
        actual fun detectLanguageFromString(
            textToIdentify: String,
            languageIdentified: DefaultActionWithStringParam
        ) {
            val languageIdentifier = LanguageIdentification.getClient()
            languageIdentifier.identifyLanguage(textToIdentify)
                .addOnSuccessListener { languageCode ->
                    languageIdentified(languageCode)
                }
                .addOnFailureListener {
                    KmpMlLogger.logErrorWithStackTrace(it.stackTraceToString())
                }
        }
    }
}