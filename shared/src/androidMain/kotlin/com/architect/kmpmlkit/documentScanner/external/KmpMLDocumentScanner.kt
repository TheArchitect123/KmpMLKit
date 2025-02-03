package com.architect.neuralKmp.documentScanner.external

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.architect.kmpessentials.logging.KmpLogging
import com.architect.kmpessentials.toast.KmpToast
import com.architect.neuralKmp.KmpMLAndroid
import com.architect.neuralKmp.documentScanner.external.config.ScannerOptions
import com.architect.neuralKmp.typealiases.DefaultManyStringsAction
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_PDF
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning

actual class KmpMLDocumentScanner {
    actual companion object {
        internal lateinit var resultLauncher: ActivityResultLauncher<IntentSenderRequest>
        internal var action: DefaultManyStringsAction? = null

        actual fun openExternalDocumentScanner(options: ScannerOptions, action: DefaultManyStringsAction) {
            this.action = action
            TranslateLanguage.THAI
            val genOptions = GmsDocumentScannerOptions.Builder()
                .setGalleryImportAllowed(options.allowGalleryImport)
                .setPageLimit(options.pageLimit)
                .setScannerMode(SCANNER_MODE_FULL)
            if (options.scanToPdf && options.scanToImage) {
                genOptions.setResultFormats(RESULT_FORMAT_JPEG, RESULT_FORMAT_PDF)
            } else {
                if (options.scanToPdf) {
                    genOptions.setResultFormats(RESULT_FORMAT_PDF)
                }
                if (options.scanToImage) {
                    genOptions.setResultFormats(RESULT_FORMAT_JPEG)
                }
            }

            val scanner = GmsDocumentScanning.getClient(genOptions.build())
            scanner.getStartScanIntent(KmpMLAndroid.getCurrentActivityContext())
                .addOnSuccessListener { intentSender ->
                    resultLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
                }
                .addOnFailureListener {
                    KmpLogging.writeError("KMP-ML-KIT", it.stackTraceToString())
                    KmpToast.showToastShort("mmm...something went wrong")
                }
        }
    }
}