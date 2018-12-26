package com.ideabaker.samples.springsecurity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 2018-12-25 18:25
 */
@Component
public class PublicKeyReader {
    @Value("public.txt")
    ClassPathResource resource;

    RSAPublicKey publicKey() throws Exception {
        String publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        publicKey = publicKey.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));

        return (RSAPublicKey) kf.generatePublic(keySpecX509);
    }
}
