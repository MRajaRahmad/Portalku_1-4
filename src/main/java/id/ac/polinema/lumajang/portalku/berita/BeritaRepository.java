package id.ac.polinema.lumajang.portalku.berita;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.ac.polinema.lumajang.portalku.berita.dto.BeritaRingkasResponse;

public interface BeritaRepository
        extends JpaRepository<Berita, Long>, BeritaRepositoryCustom {

    @Query(
        value = """
            SELECT new id.ac.polinema.lumajang.portalku.berita.dto.BeritaRingkasResponse(
                b.id,
                b.judul,
                k.nama,
                b.tanggalTerbit,
                b.jumlahDilihat
            )
            FROM Berita b
            JOIN b.kategori k
            WHERE (:kategori IS NULL OR k.nama = :kategori)
              AND (
                  :cari IS NULL
                  OR LOWER(b.judul) LIKE LOWER(CONCAT('%', :cari, '%'))
              )
            """,
        countQuery = """
            SELECT COUNT(b)
            FROM Berita b
            JOIN b.kategori k
            WHERE (:kategori IS NULL OR k.nama = :kategori)
              AND (
                  :cari IS NULL
                  OR LOWER(b.judul) LIKE LOWER(CONCAT('%', :cari, '%'))
              )
            """
    )
    Page<BeritaRingkasResponse> cari(
            @Param("kategori") String kategori,
            @Param("cari") String cari,
            Pageable pageable
    );

    @Modifying(
        clearAutomatically = true,
        flushAutomatically = true
    )
    @Query("""
        UPDATE Berita b
        SET b.jumlahDilihat = b.jumlahDilihat + 1
        WHERE b.id = :id
        """)
    int tambahJumlahDilihat(@Param("id") Long id);
}