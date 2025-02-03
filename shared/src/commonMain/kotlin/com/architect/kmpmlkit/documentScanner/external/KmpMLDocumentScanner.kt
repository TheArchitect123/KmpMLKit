package com.architect.neuralKmp.documentScanner.external

import com.architect.neuralKmp.documentScanner.external.config.ScannerOptions
import com.architect.neuralKmp.typealiases.DefaultManyStringsAction

expect class KmpMLDocumentScanner {
    companion object {
        fun openExternalDocumentScanner(options: ScannerOptions, action: DefaultManyStringsAction)
    }
}