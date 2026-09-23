package id.ac.polinema.lumajang.portalku.pengumuman;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanRequest;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanResponse;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanRingkasResponse;

@RestController
@RequestMapping("/api/pengumuman")
public class PengumumanController {

    private final PengumumanService pengumumanService;

    public PengumumanController(
            PengumumanService pengumumanService) {
        this.pengumumanService = pengumumanService;
    }

    @GetMapping
    public List<PengumumanRingkasResponse> semua() {
        return pengumumanService.cariSemua();
    }

    @GetMapping("/{id}")
    public PengumumanResponse satu(
            @PathVariable Long id) {

        return pengumumanService.cariSatu(id);
    }

    @PostMapping
    public ResponseEntity<PengumumanResponse> tambah(
            @Valid @RequestBody PengumumanRequest req) {

        PengumumanResponse hasil =
                pengumumanService.tambah(req);

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

    @PutMapping("/{id}")
    public PengumumanResponse ubah(
            @PathVariable Long id,
            @Valid @RequestBody PengumumanRequest req) {

        return pengumumanService.ubah(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hapus(@PathVariable Long id) {
        pengumumanService.hapus(id);
    }
}