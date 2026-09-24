package id.ac.polinema.lumajang.portalku.pengumuman;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import id.ac.polinema.lumajang.portalku.pengumuman.dto.LampiranRequest;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.LampiranResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pengumuman/{pengumumanId}/lampiran")
@RequiredArgsConstructor
@Tag(
    name = "Lampiran",
    description = "Pengelolaan lampiran pengumuman"
)
public class LampiranController {

    private final LampiranService lampiranService;

    @Operation(
        summary = "Daftar lampiran pengumuman"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Daftar lampiran berhasil diambil"
    )
    @GetMapping
    public List<LampiranResponse> semua(
            @PathVariable Long pengumumanId) {

        return lampiranService.cariSemua(pengumumanId);
    }

    @Operation(
        summary = "Menambah lampiran pengumuman"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Lampiran berhasil dibuat"
        ),
        @ApiResponse(
            responseCode = "422",
            description = "Data lampiran tidak valid"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Pengumuman tidak ditemukan"
        )
    })
    @PostMapping
    public ResponseEntity<LampiranResponse> tambah(
            @PathVariable Long pengumumanId,
            @Valid @RequestBody LampiranRequest req) {

        LampiranResponse hasil =
                lampiranService.tambah(
                        pengumumanId,
                        req);

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
}