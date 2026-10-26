package id.ac.polinema.lumajang.portalku.berita.dto;

import java.time.LocalDateTime;

public record BeritaRingkasResponse(
        Long id,
        String judul,
        String namaKategori,
        LocalDateTime tanggalTerbit,
        Integer jumlahDilihat
) {}