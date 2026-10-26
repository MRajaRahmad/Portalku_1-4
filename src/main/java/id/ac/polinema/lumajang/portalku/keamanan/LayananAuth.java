
package id.ac.polinema.lumajang.portalku.keamanan;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import id.ac.polinema.lumajang.portalku.keamanan.dto.PermintaanLogin;
import id.ac.polinema.lumajang.portalku.keamanan.dto.TanggapanLogin;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LayananAuth {

    private final AuthenticationManager pengelolaAutentikasi;
    private final PenggunaRepository penggunaRepository;
    private final TokenPenyegarRepository tokenPenyegarRepository;
    private final LayananJwt layananJwt;

    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${jwt.kedaluwarsa-akses}")
    private long masaBerlakuAkses;

    @Value("${jwt.kedaluwarsa-penyegar}")
    private long masaBerlakuPenyegar;

    @Transactional
    public TanggapanLogin login(PermintaanLogin req) {
        try {
            pengelolaAutentikasi.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.email(),
                            req.sandi()));
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(
                    "Email atau kata sandi tidak benar");
        }

        Pengguna pengguna = penggunaRepository
                .findByEmailAndAktifTrue(req.email())
                .orElseThrow(() -> new BadCredentialsException(
                        "Email atau kata sandi tidak benar"));

        return buatTanggapanLogin(pengguna);
    }

    @Transactional
    public TanggapanLogin segarkan(String nilaiTokenPenyegar) {
        TokenPenyegar token = tokenPenyegarRepository
                .findByToken(nilaiTokenPenyegar)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Token penyegar tidak sah atau kedaluwarsa"));

        LocalDateTime sekarang = LocalDateTime.now(ZoneOffset.UTC);

        if (token.isDicabut()
                || !token.getKedaluwarsa().isAfter(sekarang)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Token penyegar tidak sah atau kedaluwarsa");
        }

        // Cabut token lama sebelum membuat pasangan token baru.
        token.setDicabut(true);
        tokenPenyegarRepository.save(token);

        return buatTanggapanLogin(token.getPengguna());
    }

    @Transactional
    public void cabut(String nilaiTokenPenyegar) {
        tokenPenyegarRepository.findByToken(nilaiTokenPenyegar)
                .ifPresent(token -> {
                    token.setDicabut(true);
                    tokenPenyegarRepository.save(token);
                });
    }

    private TanggapanLogin buatTanggapanLogin(Pengguna pengguna) {
        String tokenAkses = layananJwt.terbitkan(
                pengguna.getEmail(),
                pengguna.getPeran().name());

        // Buat token penyegar acak dengan 32 byte.
        byte[] bytesToken = new byte[32];
        secureRandom.nextBytes(bytesToken);

        String tokenPenyegar = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytesToken);

        TokenPenyegar catatanToken = new TokenPenyegar();
        catatanToken.setToken(tokenPenyegar);
        catatanToken.setPengguna(pengguna);
        catatanToken.setKedaluwarsa(
                LocalDateTime.now(ZoneOffset.UTC)
                        .plus(masaBerlakuPenyegar, ChronoUnit.MILLIS));
        catatanToken.setDicabut(false);

        tokenPenyegarRepository.save(catatanToken);

        return new TanggapanLogin(
                tokenAkses,
                tokenPenyegar,
                "Bearer",
                masaBerlakuAkses / 1000,
                pengguna.getNama(),
                pengguna.getPeran().name());
    }
}
