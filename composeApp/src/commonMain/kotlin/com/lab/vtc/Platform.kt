package com.lab.vtc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform