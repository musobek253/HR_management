package uz.muso.hrmanagement.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.muso.hrmanagement.entitiy.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    static long expireTime = 1000*60*60*24*7 ;// bir hafta
    static String secretKey = "Maxfiyso'zjwtuchunblockchain";
    public String generateToken(String username, Set<Role> roles) {
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;
    }
}
