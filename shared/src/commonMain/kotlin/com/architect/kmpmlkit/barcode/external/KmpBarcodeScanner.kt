package com.architect.kmpmlkit.barcode.external

import com.architect.kmpmlkit.barcode.BarcodeScannerOptions
import com.architect.kmpmlkit.typealiases.DefaultActionWithStringParam

expect class KmpBarcodeScanner {
    companion object {
        fun openExternalBarcodeScanner(
            options: BarcodeScannerOptions,
            action: DefaultActionWithStringParam
        )
    }
}