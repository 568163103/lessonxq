package com.xq;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class Test {

    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jws  = Jwts.builder().setSubject("joe").signWith(key).compact();
        System.out.println(jws);

        try {

            System.out.println(Jwts.parser().setSigningKey(key).parseClaimsJws(jws));

            //OK, we can trust this JWT

        } catch (JwtException e) {

            //don't trust the JWT!
        }
    }
}
