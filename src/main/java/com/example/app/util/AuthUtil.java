package com.example.app.util;

import com.example.app.dto.auth.UserAuthentication;
import com.example.app.exception.AuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;


public class AuthUtil {
    public static String hashPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }

    public static String generateToken(Map<String, Object> payload, String secretKey) {
        // Set current date
        Date now = new Date();

        // Build the JWT token
        return Jwts.builder()
                .setClaims(payload) // Set payload
                .setIssuedAt(now) // Set issued date
                .setExpiration(new Date(now.getTime() + 10 * 60 * 1000)) // Set expiration date (10 minutes from now)
                .signWith(SignatureAlgorithm.HS256,secretKey) // Sign with HMAC using SHA-256 algorithm and the secret key
                .compact(); // Compact into a URL-safe string
    }

//    public static Key getKey() {
//        byte [] keyBytes = Decoders.BASE64.decode("76324t2yr2c3293ynr87n27tcx87t478x4ct78cxiu43t3748");
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

    public static Map<String, Object> getPayloadJwt(String token, String secretKey) {
        try {
            return (Map<String, Object>) Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthException("TOKEN_EXPIRED", "Phiên đăng nhập đã hết hạn");
        } catch (SignatureException e) {
            throw new AuthException("TOKEN_FAIL", "Token không khả dụng");
        } catch (Exception e) {
            throw new AuthException("TOKEN_FAIL", "Lỗi không xác định trong token");
        }
    }

    public static UserAuthentication convertJwtToUser(String token, String secretKey) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> payload = getPayloadJwt(token, secretKey);
            return objectMapper.convertValue(payload, UserAuthentication.class);
        } catch (ExpiredJwtException e) {
            throw new AuthException("TOKEN_EXPIRED", "Phiên đăng nhập đã hết hạn");
        } catch (SignatureException e) {
            throw new AuthException("TOKEN_FAIL", "Token không khả dụng");
        } catch (Exception e) {
            throw new AuthException("TOKEN_FAIL", "Lỗi không xác định trong token");
        }
    }

//    public static Claims extractAllClaims(String token){
//        return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody();
//    }

    // trich xuat 1 claim
//    public  static <T> T extractClaims(String token, Function<Claims,T> claimsTFunction){
//        Claims claims = extractAllClaims(token);
//        return claimsTFunction.apply(claims);
//    }


    // kiem tra thoi gian token
//    public static Date extractExpiration(String token){
//        return extractClaims(token,Claims :: getExpiration);
//    }
//    public String extractUserName(String token){
//        return extractClaims(token,Claims :: getSubject);
//    }

    // kiem tra token het han

//    public Boolean isTokenExpired(String token){
//        return extractExpiration(token).before(new Date());
//    }

    // kiem tra tinh hop le

    // ten dang nhap trung vs db va con tg token
    public boolean validateToken(String token){
        return true;
    }

    public static UserAuthentication getUserAuthentication() {
        return (UserAuthentication) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }


    public static Long getUserId() {
        UserAuthentication userAuthentication = getUserAuthentication();
        return Optional.ofNullable(userAuthentication).map(UserAuthentication::getUserId)
                .orElse(null);
    }



}
