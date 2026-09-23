package id.ac.polinema.lumajang.portalku.pengumuman;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanRequest;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanResponse;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanRingkasResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ProblemDetail;

@RestController
@RequestMapping("/api/pengumuman")
@RequiredArgsConstructor
@Tag(
    name = "Pengumuman",
    description = "Pengelolaan pengumuman akademik"
)
public class PengumumanController {

    private final PengumumanService pengumumanService;

    @Operation(
        summary = "Daftar seluruh pengumuman",
        description = "Mengembalikan bentuk ringkas tanpa isi lengkap pengumuman"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Daftar berhasil diambil"
    )
    @GetMapping
    public List<PengumumanRingkasResponse> semua() {
        return pengumumanService.cariSemua();
    }

    @GetMapping("/{id}")
    public PengumumanResponse satu(
            @PathVariable Long id) {

        return pengumumanService.cariSatu(id);
    }

    @Operation(
        summary = "Menambah pengumuman baru"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Pengumuman berhasil dibuat"
        ),
        @ApiResponse(
            responseCode = "422",
            description = "Data tidak lolos validasi",
            content = @Content(
                schema = @Schema(implementation = ProblemDetail.class)
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Judul sudah dipakai",
            content = @Content(
                schema = @Schema(implementation = ProblemDetail.class)
            )
        )
    })
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