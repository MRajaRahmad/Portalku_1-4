package id.ac.polinema.lumajang.portalku.pengumuman;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import id.ac.polinema.lumajang.portalku.kategori.Kategori;

public interface PengumumanRepository
        extends JpaRepository<Pengumuman, Integer> {

    List<Pengumuman> findByKategori(Kategori kategori);

    @Query("""
        SELECT p
        FROM Pengumuman p
        JOIN FETCH p.kategori
        """)
    List<Pengumuman> findAllWithKategori();
}