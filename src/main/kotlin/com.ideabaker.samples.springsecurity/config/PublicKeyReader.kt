package com.ideabaker.samples.springsecurity.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.util.FileCopyUtils
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 2018-12-29 10:43
 */
@Component
class PublicKeyReader {
    @Value("public.txt")
    internal var resource: ClassPathResource? = null

    @Throws(Exception::class)
    internal fun publicKey(): RSAPublicKey {
        var publicKey = String(FileCopyUtils.copyToByteArray(resource!!.inputStream))
        publicKey = publicKey.replace("\\n".toRegex(), "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
        val kf = KeyFactory.getInstance("RSA")
        val keySpecX509 = X509EncodedKeySpec(Base64.getDecoder().decode(publicKey))

        return kf.generatePublic(keySpecX509) as RSAPublicKey
    }
}
