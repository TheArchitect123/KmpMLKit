package com.architect.kmpmlkit.barcode.external

import com.architect.kmpmlkit.documentScanner.external.config.ScannerOptions

expect class KmpBarcodeScanner {
    companion object {
        fun openExternalBarcodeScanner(options: ScannerOptions, action: String)
    }
}