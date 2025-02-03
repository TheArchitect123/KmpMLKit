package com.architect.neuralKmp.barcode.external

import com.architect.kmpessentials.KmpAndroid
import com.architect.neuralKmp.barcode.BarcodeScannerOptions
import com.architect.neuralKmp.typealiases.DefaultActionWithStringParam
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

actual class KmpMLBarcodeScanner {
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