package com.architect.neuralKmp.smartReply

import com.architect.neuralKmp.typealiases.DefaultManyStringsAction

expect class KmpMLSmartReply {
    companion object{
        fun getSmartReplyFromBot(messageFromUser: String, action: DefaultManyStringsAction)
    }
}