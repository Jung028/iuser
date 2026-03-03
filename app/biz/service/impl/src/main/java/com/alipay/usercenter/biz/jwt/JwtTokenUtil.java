package com.alipay.usercenter.biz.jwt;

import com.alipay.usercenter.common.service.facade.item.OtpChallengeItem;
import com.alipay.usercenter.core.model.UserInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenUtil {

    /**
     * private Key file path
     */
    private String privateKeyPath = ("/private_key.pem");

    /**
     * Generate a JWT token for the given user information
     *
     * @param userInfo the user information
     * @return the generated JWT token
     */
    public String generateTokenForUserInfo(UserInfo userInfo) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // expires in 10 minutes
        Date expiry = new Date(nowMillis + 10 * 60 * 1000);

        return Jwts.builder()
                .setSubject(String.valueOf(userInfo.getUserId()))
                .claim("status", userInfo.getStatus())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setIssuer("user-center")
                .setAudience("business-center")
                .signWith(loadPrivateKey(privateKeyPath), SignatureAlgorithm.RS256)
                .compact();
    }

    // create another for otp challenge
    public String generateTokenForOtpChallenge(OtpChallengeItem challenge){
        long currentMillis = System.currentTimeMillis();
        Date now = new Date(currentMillis);
        // 5 min expiration
        Date expiry = new Date(currentMillis + 5 * 60 * 1000);

        return Jwts.builder()
                .setSubject("otp_verification")
                .claim("challengeId", challenge.getChallengeId())
                .claim("phoneNo", challenge.getPhoneNo())
                .claim("scene", challenge.getSceneCode())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setIssuer("user-center")
                .setAudience("business-center")
                .signWith(loadPrivateKey(privateKeyPath), SignatureAlgorithm.RS256)
                .compact();
    }

    public static PrivateKey loadPrivateKey(String filePath) {
        try (InputStream is = JwtTokenUtil.class.getResourceAsStream(filePath)) {
            if (is == null) {
                throw new RuntimeException("Private key not found: " + filePath);
            }
            byte[] keyBytes = is.readAllBytes();
            String keyPem = new String(keyBytes)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] decoded = Base64.getDecoder().decode(keyPem);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }
}
