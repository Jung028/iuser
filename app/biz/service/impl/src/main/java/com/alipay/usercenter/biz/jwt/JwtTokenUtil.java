package com.alipay.usercenter.biz.jwt;

import com.alipay.usercenter.common.service.facade.item.OtpChallengeItem;
import com.alipay.usercenter.core.domain.UserInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;


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
                .signWith(loadPrivateKey(privateKeyPath))
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
                .signWith(loadPrivateKey(privateKeyPath), SignatureAlgorithm.HS256)
                .compact();
    }

    public static PrivateKey loadPrivateKey(String filePath) {
        String keyPem = null;
        try {
            keyPem = new String(Files.readAllBytes(Paths.get(filePath)))
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] keyBytes = Base64.getDecoder().decode(keyPem);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("EC");
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
