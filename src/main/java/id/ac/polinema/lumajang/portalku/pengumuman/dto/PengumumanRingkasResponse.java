package id.ac.polinema.lumajang.portalku.pengumuman.dto;

import java.time.LocalDateTime;

public record PengumumanRingkasResponse(
        Long id,
        String judul,
        String namaKategori,
        LocalDateTime tanggalTerbit,
        Integer jumlahDilihat
) {}