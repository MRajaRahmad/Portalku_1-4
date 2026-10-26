package id.ac.polinema.lumajang.portalku.berita;

import java.util.List;

import id.ac.polinema.lumajang.portalku.berita.dto.BeritaRingkasResponse;

public interface BeritaRepositoryCustom {

    List<BeritaRingkasResponse> cariNative(
            String kategori,
            String cari,
            int offset,
            int limit,
            List<String> sort
    );

    long hitungNative(
            String kategori,
            String cari
    );
}