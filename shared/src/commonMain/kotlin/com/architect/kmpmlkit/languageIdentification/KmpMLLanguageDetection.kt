package com.architect.neuralKmp.languageIdentification

import com.architect.neuralKmp.typealiases.DefaultActionWithStringParam

expect class KmpMLLanguageDetection {
    companion object {
        fun detectLanguageFromString(textToIdentify: String,
                                     languageIdentified: DefaultActionWithStringParam)
    }
}