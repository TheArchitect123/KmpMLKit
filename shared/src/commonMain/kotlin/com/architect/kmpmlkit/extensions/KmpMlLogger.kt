package com.architect.kmpmlkit.extensions

import com.architect.kmpessentials.logging.KmpLogging
import com.architect.kmpessentials.mainThread.KmpMainThread
import com.architect.kmpessentials.toast.KmpToast

object KmpMlLogger {
    fun logErrorWithStackTrace(stackTrace: String) {
        KmpLogging.writeError("KMP-ML-KIT", stackTrace)
        KmpMainThread.runViaMainThread {
            KmpToast.showToastShort("mmm...something went wrong")
        }
    }
}