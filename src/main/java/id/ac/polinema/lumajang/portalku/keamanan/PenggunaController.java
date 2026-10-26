
package id.ac.polinema.lumajang.portalku.keamanan;

import java.util.List;

import id.ac.polinema.lumajang.portalku.keamanan.dto.PermintaanPengguna;
import id.ac.polinema.lumajang.portalku.keamanan.dto.PenggunaRingkasResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pengguna")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class PenggunaController {

    private final PenggunaRepository penggunaRepository;
    private final PasswordEncoder pengkodeSandi;

    // =========================================
    // MENAMPILKAN DAFTAR PENGGUNA
    // ADMIN saja
    // =========================================

    @GetMapping
    public List<PenggunaRingkasResponse> semua() {

        return penggunaRepository.findAll()
                .stream()
                .map(p -> new PenggunaRingkasResponse(
                        p.getId(),
                        p.getEmail(),
                        p.getNama(),
                        p.getPeran().name()
                ))
                .toList();
    }

    // =========================================
    // MEMBUAT AKUN PENGGUNA
    // ADMIN saja
    // =========================================

    @PostMapping
    public ResponseEntity<PenggunaRingkasResponse> tambah(
            @Valid @RequestBody PermintaanPengguna req) {

        String email = req.email().trim();

        // Tolak jika email sudah digunakan oleh akun aktif.
        if (penggunaRepository
                .findByEmailAndAktifTrue(email)
                .isPresent()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email sudah digunakan."
            );
        }

        Pengguna pengguna = new Pengguna();
        pengguna.setEmail(email);
        pengguna.setNama(req.nama().trim());

        // Kata sandi wajib di-hash menggunakan BCrypt.
        pengguna.setSandi(
                pengkodeSandi.encode(req.sandi())
        );

        pengguna.setPeran(req.peran());
        pengguna.setAktif(true);

        Pengguna tersimpan =
                penggunaRepository.save(pengguna);

        PenggunaRingkasResponse respons =
                new PenggunaRingkasResponse(
                        tersimpan.getId(),
                        tersimpan.getEmail(),
                        tersimpan.getNama(),
                        tersimpan.getPeran().name()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respons);
    }
}
