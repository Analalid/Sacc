package com.example.sacc.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.sacc.Entity.Account;
import com.example.sacc.Exception.LocalRuntimeException;
import com.example.sacc.Service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    private final RedisService redisService;

    public JwtUtil(RedisService redisService) {
        this.redisService = redisService;
    }

    public String createToken(Account account) {
        Calendar time = Calendar.getInstance();
        time.add(Calendar.HOUR, expiration);
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("stu_id", account.getStuId());
        builder.withClaim("role", account.getRole());
        builder.withExpiresAt(time.getTime());
        return builder.sign(Algorithm.HMAC256(secret));
    }

    public Map<String, Claim> getClaims(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaims();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new LocalRuntimeException("token错误");
        }
    }

    public Account getAccount(String token) {
        Map<String, Claim> claims = this.getClaims(token);
        Account account = new Account();
        account.setStuId(claims.get("stu_id").asString());
        account.setRole(claims.get("role").asInt());
        return account;
    }

}
