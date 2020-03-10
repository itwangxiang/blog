package cn.todev.exoplay

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AesUtils(private val transformation: String = "AES/CTR/NoPadding", key: String = "1234567890123456") {

    private lateinit var ivSpec: IvParameterSpec
    private lateinit var keySpec: SecretKeySpec

    init {
        try {
            val keyBytes = key.toByteArray()
            val buf = ByteArray(16)
            var i = 0
            while (i < keyBytes.size && i < buf.size) {
                buf[i] = keyBytes[i]
                i++
            }
            keySpec = SecretKeySpec(buf, "AES")
            ivSpec = IvParameterSpec(ByteArray(16))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getCipher(): Cipher? {
        try {
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            return cipher
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun encrypt(origData: ByteArray): ByteArray {
        try {
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            return cipher.doFinal(origData)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return byteArrayOf()
    }

    fun decrypt(encrypted: ByteArray): ByteArray {
        try {
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            return cipher.doFinal(encrypted)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return byteArrayOf()
    }
}