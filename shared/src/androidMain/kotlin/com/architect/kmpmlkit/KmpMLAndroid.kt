package com.architect.neuralKmp

import android.app.Activity.RESULT_OK
import android.app.Application
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.architect.kmpessentials.logging.KmpLogging
import com.architect.kmpessentials.toast.KmpToast
import com.architect.neuralKmp.documentScanner.external.KmpMLDocumentScanner
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult

class KmpMLAndroid {
    companion object {
        private var hasRegistered = false

        internal var applicationContext: Application? = null
        internal var clientAppContext: FragmentActivity? = null

        internal fun getCurrentActivityContext(): FragmentActivity {
            return clientAppContext!!
        }

        internal fun getCurrentApplicationContext(): Application {
            return applicationContext!!
        }

        fun preRegisterApplicationContext(
            appContext: Application,
        ) {
            if (!hasRegistered) {
                applicationContext = appContext

                // any preregistration (before the activity is registered goes here)
                hasRegistered = true
            }
        }

        fun initializeMLKit(
            context: FragmentActivity
        ) {
            clientAppContext = context

            // register application
            preRegisterApplicationContext(context.application)
            registerAllContracts()
        }

        private fun registerAllContracts() {
            KmpMLDocumentScanner.resultLauncher = clientAppContext!!.registerForActivityResult(
                ActivityResultContracts.StartIntentSenderForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val cpages = mutableListOf<String>()
                    val result =
                        GmsDocumentScanningResult.fromActivityResultIntent(result.data)
                    result?.getPages()?.let { pages ->
                        for (page in pages) {
                            cpages.add(pages.get(0).getImageUri().path ?: "")
                        }
                    }
                    result?.getPdf()?.let { pdf ->
                        cpages.add(pdf.getUri().path ?: "")
                    }

                    KmpMLDocumentScanner.action?.invoke(cpages)
                } else {
                    KmpLogging.writeError("KMP-ML-KIT", "${result.data?.extras}")
                    KmpToast.showToastShort("mmm...something went wrong")
                }
            }
        }
    }
}