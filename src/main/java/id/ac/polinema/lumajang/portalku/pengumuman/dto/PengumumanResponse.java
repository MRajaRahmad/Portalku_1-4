package id.ac.polinema.lumajang.portalku.pengumuman.dto;

import java.time.LocalDate;

public record PengumumanResponse(
        Integer id,
        String judul,
        String isi,
        LocalDate tanggalTerbit,
        Integer jumlahDilihat,
        String namaKategori,
        int jumlahLampiran
) {
}