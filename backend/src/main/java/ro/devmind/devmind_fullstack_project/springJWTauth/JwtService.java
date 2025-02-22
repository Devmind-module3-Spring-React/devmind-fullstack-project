package ro.devmind.devmind_fullstack_project.springJWTauth;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.accessTokenValiditityMS}")
    private int jwtExpirationMs;

    public String createToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .header()
                .and()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public String validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        JwtParser parser = Jwts.parser()
                .verifyWith(key)
                .build();
        //If Jwt is not valid -> throw Exception
        try {
            //Token  Bearer "<<SPACE>>" eyJhbchdhaadqdadasda1.da21dad21.dad2rsaz
            Jwt jwt = parser.parse(token.substring(token.indexOf(" ") + 1)); //use only what is after <<SPACE>>
            return ((DefaultClaims) (jwt.getPayload())).getSubject(); //"sub" part of JWT is the username ->> see JWT.io

        } catch (Exception ex) {
            throw new UsernameNotFoundException("Invalid token");
        }
    }
}
