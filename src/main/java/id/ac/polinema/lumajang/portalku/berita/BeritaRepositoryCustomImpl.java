package id.ac.polinema.lumajang.portalku.berita;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import id.ac.polinema.lumajang.portalku.berita.dto.BeritaRingkasResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class BeritaRepositoryCustomImpl implements BeritaRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BeritaRingkasResponse> cariNative(
            String kategori,
            String cari,
            int offset,
            int limit,
            List<String> sort) {

        StringBuilder sql = new StringBuilder("""
            SELECT
                b.id,
                b.judul,
                k.nama,
                b.tanggal_terbit,
                b.jumlah_dilihat
            FROM berita b
            JOIN kategori k
                ON k.id = b.kategori_id
            WHERE (
                CAST(:kategori AS VARCHAR) IS NULL
                OR k.nama = CAST(:kategori AS VARCHAR)
            )
            AND (
                CAST(:cari AS VARCHAR) IS NULL
                OR LOWER(b.judul) LIKE LOWER(
                    CONCAT('%', CAST(:cari AS VARCHAR), '%')
                )
            )
            ORDER BY
            """);

        sql.append(buatOrderBy(sort)).append("\n");

        sql.append("""
            LIMIT :limit
            OFFSET :offset
            """);

        var query = entityManager.createNativeQuery(
                sql.toString()
        );

        query.setParameter("kategori", kategori);
        query.setParameter("cari", cari);
        query.setParameter("limit", limit);
        query.setParameter("offset", offset);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();

        List<BeritaRingkasResponse> hasil =
                new ArrayList<>();

        for (Object[] row : rows) {

            hasil.add(
                new BeritaRingkasResponse(
                    ((Number) row[0]).longValue(),
                    (String) row[1],
                    (String) row[2],
                    ((Timestamp) row[3]).toLocalDateTime(),
                    ((Number) row[4]).intValue()
                )
            );
        }

        return hasil;
    }

    @Override
    public long hitungNative(
            String kategori,
            String cari) {

        String sql = """
            SELECT COUNT(b.id)
            FROM berita b
            JOIN kategori k
                ON k.id = b.kategori_id
            WHERE (
                CAST(:kategori AS VARCHAR) IS NULL
                OR k.nama = CAST(:kategori AS VARCHAR)
            )
            AND (
                CAST(:cari AS VARCHAR) IS NULL
                OR LOWER(b.judul) LIKE LOWER(
                    CONCAT('%', CAST(:cari AS VARCHAR), '%')
                )
            )
            """;

        var query = entityManager.createNativeQuery(sql);

        query.setParameter("kategori", kategori);
        query.setParameter("cari", cari);

        return ((Number) query.getSingleResult()).longValue();
    }

    private String buatOrderBy(List<String> sort) {

        if (sort == null || sort.isEmpty()) {
            return """
                b.tanggal_terbit DESC,
                b.id ASC
                """;
        }

        List<String> orderBy = new ArrayList<>();

        for (String nilai : sort) {

            String[] bagian = nilai.split(",");

            String field = bagian[0];

            String kolom;

            switch (field) {

                case "id" ->
                    kolom = "b.id";

                case "judul" ->
                    kolom = "b.judul";

                case "tanggalTerbit" ->
                    kolom = "b.tanggal_terbit";

                case "jumlahDilihat" ->
                    kolom = "b.jumlah_dilihat";

                default ->
                    throw new IllegalArgumentException(
                        "Field pengurutan tidak valid: " + field
                    );
            }

            String arah = "ASC";

            if (bagian.length > 1) {

                arah = bagian[1].equalsIgnoreCase("DESC")
                        ? "DESC"
                        : "ASC";
            }

            orderBy.add(
                kolom + " " + arah
            );
        }

        return String.join(
            ", ",
            orderBy
        );
    }
}