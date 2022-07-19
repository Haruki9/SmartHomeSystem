package com.edu.xmu.haruki.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author haruki_9
 * @date 2022/7/12
 */
@Component
public class JwtUtil {

    private final String issuer="etcGroup";
    private final String subject="smartHome";
    private final String audience="none";
    private final Long expiration=(long)60*60*1000;

    // Jwt 签名密钥，若不存在，则自动生成
    private String secret="EtcSmartHomeManagerSystemForXmuWorkTrainingUsageAndGroupByHarukiGroupCompleteByHaruki";


    /**
     *  生成Jwt Token
     *
     * @param username
     * @return
     */
    public String createToken(String username){
        return Jwts.builder()
                .setIssuer(issuer)
                .setAudience(audience)
                .setSubject(subject)
                .claim("username",username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }


    /**
     * 生成 jwt token
     *
     * @param username 用户名
     * @param exp 过期时间
     * @return
     */
    public String createToken(String username, Long exp){
        return Jwts.builder()
                .setIssuer(issuer)
                .setAudience(audience)
                .setSubject(subject)
                .claim("username",username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+exp))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }


    /**
     * 生成 jwt token
     *
     * @param claims 数据体
     * @return
     */
    public String createToken(Map<String, Object> claims){
        return Jwts.builder()
                .setIssuer(issuer)
                .setAudience(audience)
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }


    /**
     * 生成 jwt token
     *
     * @param claims 数据体
     * @param exp 过期时间
     * @return
     */
    public String createToken(Map<String, Object> claims,Long exp){
        return Jwts.builder()
                .setIssuer(issuer)
                .setAudience(audience)
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+exp))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }


    /**
     * 获取数据体Claims
     *
     * @param token
     * @return
     */
    public Claims getClaimsFromToken(String token){
        Claims claims=null;
        try{
            claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (JwtException e){
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 获取数据体中的目标内容
     *
     * @param token
     * @param targetKey
     * @return
     */
    public Object getTargetClaimsFromToken(String token,String targetKey){
        Object target=null;
        Claims claims=getClaimsFromToken(token);
        if (claims!=null)target= claims.get(targetKey);
        return target;
    }

    /**
     * 过期判断
     *
     * @param token
     * @return
     */
    public boolean judgeTokenExpired(String token){
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }


    /**
     * 合法判断
     *
     * @param token
     * @param username
     * @return
     */
    public boolean judgeTokenLegality(String token){
        String username= (String) getTargetClaimsFromToken(token,"username");
        return username!=null&&!username.isEmpty() &&!judgeTokenExpired(token);
    }


    /**
     * 刷新token
     *
     * @param refreshToken
     * @return
     */
    public String refreshToken(String refreshToken){
        if(judgeTokenExpired(refreshToken))return null;
        String refreshedToken=null;
        try {
            Claims claims=getClaimsFromToken(refreshToken);
            refreshedToken=createToken(claims,expiration*2);
        }catch (Exception e){
            e.printStackTrace();
        }
        return refreshedToken;
    }

}
