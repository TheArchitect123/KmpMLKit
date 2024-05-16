package com.architect.kmpmlkit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform