package com.architect.neuralKmp.translation

import com.architect.neuralKmp.typealiases.DefaultActionWithStringParam


actual class KmpMLTranslator {
    actual companion object {

        actual fun translateTextToTargetLanguage(
            translatingText: String,
            targetLanguage: String,
            fromLanguage: String,
            action: DefaultActionWithStringParam
        ) {

        }

        actual fun autoTranslateTextToTargetLanguage(
            translatingText: String,
            targetLanguage: String,
            action: DefaultActionWithStringParam
        ) {
        }
    }
}