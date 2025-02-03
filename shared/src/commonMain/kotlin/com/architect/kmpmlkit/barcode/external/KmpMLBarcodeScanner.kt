package com.architect.neuralKmp.barcode.external

import com.architect.neuralKmp.barcode.BarcodeScannerOptions
import com.architect.neuralKmp.typealiases.DefaultActionWithStringParam

expect class KmpMLBarcodeScanner {
    companion object {
        fun openExternalBarcodeScanner(
            options: BarcodeScannerOptions,
            action: DefaultActionWithStringParam
        )
    }
}