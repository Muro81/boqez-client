package com.lmuro.boqez


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

