package com.tfg.mariomh.v2.myApi.utils;

import com.tfg.mariomh.v2.myApi.models.dtos.UserDTO;
import com.tfg.mariomh.v2.myApi.types.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class TokenUtils {

    //@Value("${jwt.secret}")
    private static String ACCESS_TOKEN_VALIDITY_SECRET = "This is a secret key, please, just let me do the tests. I just want to end the authorization";
    //@Value("${jwt.expiration}")
    private static Long ACCESS_TOKEN_VALIDITY_SECONDS = 36000L;

    public static String createToken(String id, List<Role> roles){
        Long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        Date expiratonDate = new Date(System.currentTimeMillis()+expirationTime);
        //Map<String, Object> extra = new HashMap<>();
        //extra.put("nombre", nombre);
        return Jwts.builder()
                .setSubject(id)
                .setExpiration(expiratonDate)
                .claim("roles", new HashSet<>(roles).toString())
                //.addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_VALIDITY_SECRET.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_VALIDITY_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, getAuthorities(claims));
        }catch(JwtException e){
            return null;
        }
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(Claims claims){
        String roles = (String) claims.get("roles");
        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.split(",");
        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String aRoleName : roleNames) {
            //authorities.add(new SimpleGrantedAuthority("ROLE_"+aRoleName));
            authorities.add(new SimpleGrantedAuthority(aRoleName));
        }
        return authorities;
    }

    public static Set<SimpleGrantedAuthority> getAuthorities(UserDTO userDTO){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        //userDTO.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString())));
        userDTO.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.toString())));
        return authorities;
    }
}
