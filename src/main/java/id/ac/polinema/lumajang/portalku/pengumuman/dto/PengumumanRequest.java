package id.ac.polinema.lumajang.portalku.pengumuman.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record PengumumanRequest(
        @NotBlank(message = "Judul wajib diisi")
        @Size(max = 150, message = "Judul maksimal 150 karakter")
        String judul,

        @NotBlank(message = "Isi pengumuman wajib diisi")
        String isi,

        @NotNull(message = "Tanggal terbit wajib diisi")
        @PastOrPresent(message = "Tanggal terbit tidak boleh di masa depan")
        LocalDateTime tanggalTerbit,

        @NotNull(message = "Kategori wajib dipilih")
        Long idKategori
) {}