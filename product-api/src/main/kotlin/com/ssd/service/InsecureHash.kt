package com.ssd.service

import java.security.MessageDigest

fun insecureHash(input: String): String {
    val md5Digest = MessageDigest.getInstance("MD5") // Insecure algorithm
    val hashBytes = md5Digest.digest(input.toByteArray())
    return hashBytes.joinToString("") { "%02x".format(it) }
}