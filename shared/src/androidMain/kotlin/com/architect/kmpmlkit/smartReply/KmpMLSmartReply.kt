package com.architect.neuralKmp.smartReply

import com.architect.kmpessentials.logging.KmpLogging
import com.architect.kmpessentials.toast.KmpToast
import com.architect.neuralKmp.typealiases.DefaultManyStringsAction
import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult
import com.google.mlkit.nl.smartreply.TextMessage

actual class KmpMLSmartReply {
    actual companion object {
        actual fun getSmartReplyFromBot(messageFromUser: String, action: DefaultManyStringsAction) {
            val smartReplyGenerator = SmartReply.getClient()
            smartReplyGenerator.suggestReplies(
                listOf(
                    TextMessage.createForLocalUser(
                        messageFromUser, System.currentTimeMillis()
                    )
                )
            )
                .addOnSuccessListener { result ->
                    if (result.status == SmartReplySuggestionResult.STATUS_SUCCESS) {
                        action(result.suggestions.map { it.text })
                    }
                }
                .addOnFailureListener {
                    KmpLogging.writeError("KMP-ML-KIT", it.stackTraceToString())
                    KmpToast.showToastShort("mmm...something went wrong")
                }
        }
    }
}