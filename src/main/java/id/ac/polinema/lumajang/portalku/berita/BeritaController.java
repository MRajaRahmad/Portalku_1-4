
package id.ac.polinema.lumajang.portalku.berita;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import id.ac.polinema.lumajang.portalku.berita.dto.BeritaRequest;
import id.ac.polinema.lumajang.portalku.berita.dto.BeritaResponse;
import id.ac.polinema.lumajang.portalku.berita.dto.BeritaRingkasResponse;
import id.ac.polinema.lumajang.portalku.shared.PageResponse;

@RestController
@RequestMapping("/api/berita")
@RequiredArgsConstructor
public class BeritaController {

    private final BeritaService beritaService;

    // =========================================
    // MENAMPILKAN DAFTAR BERITA
    // Publik - fitur pagination P5 dipertahankan
    // =========================================

    @GetMapping
    public PageResponse<BeritaRingkasResponse> daftar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String kategori,
            @RequestParam(required = false) String cari,
            HttpServletRequest request) {

        String[] sortParameter =
                request.getParameterValues("sort");

        List<String> daftarSort =
                sortParameter == null
                        ? null
                        : Arrays.asList(sortParameter);

        return beritaService.cari(
                page,
                size,
                kategori,
                cari,
                daftarSort
        );
    }

    // =========================================
    // MENAMBAH BERITA
    // EDITOR dan ADMIN
    // =========================================

    @PostMapping
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    public ResponseEntity<BeritaResponse> tambah(
            @Valid @RequestBody BeritaRequest req) {

        BeritaResponse hasil =
                beritaService.tambah(req);

        URI lokasi =
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(hasil.id())
                        .toUri();

        return ResponseEntity
                .created(lokasi)
                .body(hasil);
    }

    // =========================================
    // MENGUBAH BERITA
    // EDITOR dan ADMIN
    // =========================================

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    public BeritaResponse ubah(
            @PathVariable Long id,
            @Valid @RequestBody BeritaRequest req) {

        return beritaService.ubah(id, req);
    }

    // =========================================
    // MENGHAPUS BERITA
    // ADMIN saja
    // =========================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hapus(@PathVariable Long id) {
        beritaService.hapus(id);
    }

    // =========================================
    // MENAMBAH JUMLAH DILIHAT
    // Mempertahankan endpoint P5
    // =========================================

    @PatchMapping("/{id}/dilihat")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void tambahJumlahDilihat(
            @PathVariable Long id) {

        beritaService.tambahJumlahDilihat(id);
    }
}
