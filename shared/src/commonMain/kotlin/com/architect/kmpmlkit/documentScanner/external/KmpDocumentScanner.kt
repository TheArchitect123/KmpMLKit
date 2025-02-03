package com.architect.kmpmlkit.documentScanner.external

import com.architect.kmpmlkit.documentScanner.external.config.ScannerOptions
import com.architect.kmpmlkit.typealiases.DefaultManyFilePathsAction

expect class KmpDocumentScanner {
    companion object {
        fun openExternalDocumentScanner(options: ScannerOptions, action: DefaultManyFilePathsAction)
    }
}