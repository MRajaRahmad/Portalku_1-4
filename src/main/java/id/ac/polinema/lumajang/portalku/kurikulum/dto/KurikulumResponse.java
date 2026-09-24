package id.ac.polinema.lumajang.portalku.kurikulum.dto;

import id.ac.polinema.lumajang.portalku.kurikulum.Kurikulum.Status;

public record KurikulumResponse(
        Integer id,
        String kode,
        Integer tahunBerlaku,
        Status status,
        Integer idProdi,
        String namaProdi
) {}