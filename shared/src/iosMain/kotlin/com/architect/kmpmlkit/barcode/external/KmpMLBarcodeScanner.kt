package com.architect.neuralKmp.barcode.external

import com.architect.neuralKmp.barcode.BarcodeScannerOptions
import com.architect.neuralKmp.documentScanner.external.config.ScannerOptions
import com.architect.neuralKmp.typealiases.DefaultActionWithStringParam

actual class KmpMLBarcodeScanner {
    actual companion object {
        actual fun openExternalBarcodeScanner(
            options: BarcodeScannerOptions,
            action: DefaultActionWithStringParam
        ) {

        }
    }
}