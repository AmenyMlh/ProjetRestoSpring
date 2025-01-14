package tn.uma.isamm.config;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	private final String jwtSecret = "c54e9fc5cfd0a0fe7bed9dfbc07dd0800f9957b80bbdf21dea0b61aecfe2fc3012d87c1c6e54a7a0de6722383cee1b0a4f084eac9aa4c22ddf8cf982f0241dc73f7072d9a5c12197b3df3ffde9805e3864e5fdc64c9efd6da21dc2ca5b5b2dc1fc01cf9e3fb24ec2d9fa26763dc19358e2f2d6932bc59875ae06b6b1781c30fbe04136483c375ae35201d6926a97193edb95477faa2d73db744b3a3520145c82265124add21b95152333fb58c264a9b3b60f20252cf9ad82ff531a98c7fe3d5a43953feea9ae973a852308abe4649a028243e18aafe0d9279e926af98d8766d359138cc29ee73b1e0bb7f62bf357df05e4a3ed270eb8ad35912dc32b869813aa";
    private final int jwtExpirationMs = 86400000; // 1 jour

    public String generateToken(String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        System.out.println(claims.getSubject());
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
}
