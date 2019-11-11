package com.xq.utils;

import com.xq.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author xq
 * @date 2019-11-6 22:56
 */
public class JwtUtils {

    public static final String SUBJECT = "xqclass";
    public static final String APPSECRET = "xqbase.com";
    public static final long EXPIRE = 100 * 60 * 60;

    /**
     * 生成一个token
     *
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user) {
        if (user.getId() == null || user.getName() == null || user.getHeadImg() == null) {
            return null;
        }
        String token = Jwts.builder().claim("id", user.getId())
                .claim("name", user.getName())
                .claim("head_image", user.getHeadImg())
                .setSubject(SUBJECT)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET)
                .compact();

        return token;
    }

    /**
     * 获取token
     *
     * @param token
     * @return
     */
    public static Claims checkJwt(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {

        }
        return null;

    }

    /**
     * 测试 JWT
     * @param args
     */
    public static void main(String[] args) {
        User user = new User();
        user.setId(999);
        user.setName("xq");
        user.setHeadImg("helloWorld.jpg");
        String token = JwtUtils.geneJsonWebToken(user);
        System.out.println("token=" + token);
        Claims claims = JwtUtils.checkJwt(token);
        if (claims != null) {
            int id = (int) claims.get("id");
            String name = (String) claims.get("name");
            String headImage = (String) claims.get("head_image");
            System.out.println(id);
            System.out.println(name);
            System.out.println(headImage);
        }

    }
}
