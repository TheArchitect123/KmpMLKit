package com.architect.kmpmlkit.barcode.external

import com.architect.kmpessentials.KmpAndroid
import com.architect.kmpmlkit.barcode.BarcodeScannerOptions
import com.architect.kmpmlkit.typealiases.DefaultActionWithStringParam
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

actual class KmpBarcodeScanner {
    actual companion object {
        actual fun openExternalBarcodeScanner(
            options: BarcodeScannerOptions,
            action: DefaultActionWithStringParam
        ) {
            val coptions = GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    options.defaultBarcodeType,
                    *options.barcodeType.toIntArray()
                )
                .allowManualInput()
                .enableAutoZoom() // available on 16.1.0 and higher
                .build()

            GmsBarcodeScanning.getClient(KmpAndroid.getCurrentActivityContext(), coptions)
                .startScan()
                .addOnSuccessListener { barcode ->
                    action(barcode.rawValue)
                }
                .addOnCanceledListener {
                    // Task canceled
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                }
        }
    }
}