package com.alipay.usercenter.biz.jwt;

import com.alipay.usercenter.common.service.facade.item.OtpChallengeItem;
import com.alipay.usercenter.core.model.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import static com.alipay.usercenter.biz.constants.GlobalBizConstants.*;

@Component
public class JwtUtil {
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

    /**
     * generate token for otp challenge
     * @param challenge
     * @return
     */
    public String generateTokenForOtpChallenge(OtpChallengeItem challenge){
        long currentMillis = System.currentTimeMillis();
        Date now = new Date(currentMillis);
        // 5 min expiration
        Date expiry = new Date(currentMillis + 5 * 60 * 1000);
        JwtClaims claims = JwtContextHolder.get();
        System.out.println(claims.getSubject());

        return Jwts.builder()
                .setSubject(String.valueOf(claims.getSubject()))
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

    /**
     * load private key
     * @param filePath
     * @return
     */
    public static PrivateKey loadPrivateKey(String filePath) {
        try (InputStream is = JwtUtil.class.getResourceAsStream(filePath)) {
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

    /**
     * load public key
     * @param filePath
     * @return
     */
    public static PublicKey loadPublicKey(String filePath) {
        try (InputStream is = JwtUtil.class.getResourceAsStream(filePath)) {
            if (is == null) {
                throw new RuntimeException("Public key not found: " + filePath);
            }
            byte[] keyBytes = is.readAllBytes();
            String keyPem = new String(keyBytes)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] decoded = Base64.getDecoder().decode(keyPem);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * extract token
     * @param request
     * @return
     */
    public String extractToken(HttpServletRequest request) {

        String authHeader = request.getHeader(AUTH);
        if (authHeader != null && authHeader.startsWith(BEARER)) {
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * validate
     * @param token
     * @return
     */
    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(loadPublicKey(publicKeyPath))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * parse
     * @param token
     * @return
     */
    public JwtClaims parse(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(loadPublicKey(publicKeyPath))
                .build()
                .parseClaimsJws(token)
                .getBody();

        JwtClaims jwtClaims = new JwtClaims();
        jwtClaims.setSubject(claims.getSubject());
        return jwtClaims;
    }
}
