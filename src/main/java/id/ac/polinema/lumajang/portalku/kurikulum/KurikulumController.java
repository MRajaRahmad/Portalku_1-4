package id.ac.polinema.lumajang.portalku.kurikulum;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import id.ac.polinema.lumajang.portalku.kurikulum.dto.KurikulumRequest;
import id.ac.polinema.lumajang.portalku.kurikulum.dto.KurikulumResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/kurikulum")
@RequiredArgsConstructor
@Tag(
    name = "Kurikulum",
    description = "Pengelolaan data kurikulum program studi"
)
public class KurikulumController {

    private final KurikulumService kurikulumService;

    @Operation(
        summary = "Daftar seluruh kurikulum",
        description = "Mengembalikan seluruh data kurikulum beserta informasi program studi"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Daftar kurikulum berhasil diambil"
    )
    @GetMapping
    public List<KurikulumResponse> semua() {
        return kurikulumService.cariSemua();
    }

    @Operation(
        summary = "Mencari kurikulum berdasarkan ID"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Kurikulum ditemukan"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Kurikulum tidak ditemukan"
        )
    })
    @GetMapping("/{id}")
    public KurikulumResponse satu(@PathVariable Integer id) {
        return kurikulumService.cariSatu(id);
    }

    @Operation(
        summary = "Menambah kurikulum baru"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Kurikulum berhasil dibuat"
        ),
        @ApiResponse(
            responseCode = "422",
            description = "Data tidak lolos validasi"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Kode kurikulum sudah digunakan"
        )
    })
    @PostMapping
    public ResponseEntity<KurikulumResponse> tambah(
            @Valid @RequestBody KurikulumRequest req) {

        KurikulumResponse hasil =
                kurikulumService.tambah(req);

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

    @Operation(
        summary = "Mengubah data kurikulum"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Kurikulum berhasil diubah"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Kurikulum tidak ditemukan"
        ),
        @ApiResponse(
            responseCode = "422",
            description = "Data tidak lolos validasi"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Kode kurikulum sudah digunakan"
        )
    })
    @PutMapping("/{id}")
    public KurikulumResponse ubah(
            @PathVariable Integer id,
            @Valid @RequestBody KurikulumRequest req) {

        return kurikulumService.ubah(id, req);
    }

    @Operation(
        summary = "Menghapus kurikulum"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Kurikulum berhasil dihapus"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Kurikulum tidak ditemukan"
        )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hapus(@PathVariable Integer id) {
        kurikulumService.hapus(id);
    }
}