package com.architect.smartreplies

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform