package com.architect.kmpmlkit.barcode.external

import com.architect.kmpmlkit.barcode.BarcodeScannerOptions
import com.architect.kmpmlkit.documentScanner.external.config.ScannerOptions
import com.architect.kmpmlkit.typealiases.DefaultActionWithStringParam

actual class KmpBarcodeScanner {
    actual companion object {
        actual fun openExternalBarcodeScanner(
            options: BarcodeScannerOptions,
            action: DefaultActionWithStringParam
        ) {

        }
    }
}