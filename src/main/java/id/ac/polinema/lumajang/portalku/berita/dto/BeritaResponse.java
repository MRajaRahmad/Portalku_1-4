
package id.ac.polinema.lumajang.portalku.berita.dto;

import java.time.LocalDateTime;

public record BeritaResponse(
        Long id,
        String judul,
        String isi,
        LocalDateTime tanggalTerbit,
        Integer jumlahDilihat,
        String namaKategori
) {
}
