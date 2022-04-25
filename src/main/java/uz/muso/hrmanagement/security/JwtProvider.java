package uz.muso.hrmanagement.security;


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
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    //token aynan yaroqli va bizniki ekanligini tekshirish
    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //tokendan uni usernameni ajratib olish un metod
    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();//username
    }
}
