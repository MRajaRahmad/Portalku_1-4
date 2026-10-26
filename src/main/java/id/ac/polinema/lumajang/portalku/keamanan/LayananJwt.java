package id.ac.polinema.lumajang.portalku.keamanan;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class LayananJwt {

    private final SecretKey kunci;
    private final long masaBerlakuAkses;

    public LayananJwt(
            @Value("${jwt.secret}") String rahasia,
            @Value("${jwt.kedaluwarsa-akses}") long masaBerlakuAkses) {

        this.kunci = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(rahasia));

        this.masaBerlakuAkses = masaBerlakuAkses;
    }

    public String terbitkan(String email, String peran) {
        Instant sekarang = Instant.now();

        return Jwts.builder()
                .subject(email)
                .claim("peran", peran)
                .issuedAt(Date.from(sekarang))
                .expiration(Date.from(
                        sekarang.plusMillis(masaBerlakuAkses)))
                .signWith(kunci)
                .compact();
    }

    public String bacaSubjek(String token) {
        return Jwts.parser()
                .verifyWith(kunci)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}