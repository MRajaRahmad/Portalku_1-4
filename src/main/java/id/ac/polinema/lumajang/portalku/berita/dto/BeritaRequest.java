
package id.ac.polinema.lumajang.portalku.berita.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record BeritaRequest(

        @NotBlank(message = "Judul berita wajib diisi")
        @Size(max = 255, message = "Judul maksimal 255 karakter")
        String judul,

        @NotBlank(message = "Isi berita wajib diisi")
        String isi,

        @NotNull(message = "Tanggal terbit wajib diisi")
        @PastOrPresent(message = "Tanggal terbit tidak boleh di masa depan")
        LocalDateTime tanggalTerbit,

        @NotNull(message = "Kategori wajib dipilih")
        Long idKategori

) {
}
