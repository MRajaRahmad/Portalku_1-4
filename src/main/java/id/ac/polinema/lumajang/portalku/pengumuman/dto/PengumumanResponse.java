package id.ac.polinema.lumajang.portalku.pengumuman.dto;

import java.time.LocalDateTime;

public record PengumumanResponse(
        Long id,
        String judul,
        String isi,
        LocalDateTime tanggalTerbit,
        Integer jumlahDilihat,
        String namaKategori,
        int jumlahLampiran
) {}