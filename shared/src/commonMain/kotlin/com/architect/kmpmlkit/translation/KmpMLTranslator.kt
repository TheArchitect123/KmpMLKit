package com.architect.neuralKmp.translation

import com.architect.neuralKmp.typealiases.DefaultActionWithStringParam

expect class KmpMLTranslator {

    companion object {
        /**
         * Translates the language of the string passed (From), and translates to the target language
         * */
        fun translateTextToTargetLanguage(
            translatingText: String,
            targetLanguage: String,
            fromLanguage: String = TargetLanguage.ENGLISH,
            action: DefaultActionWithStringParam
        )

        /**
         * Auto detects the language of the string and translates to the target language
         * This requires adding "enableLanguageDetection=true" in your gradle.properties file to work
         * */
        fun autoTranslateTextToTargetLanguage(
            translatingText: String,
            targetLanguage: String,
            action: DefaultActionWithStringParam
        )
    }

}

