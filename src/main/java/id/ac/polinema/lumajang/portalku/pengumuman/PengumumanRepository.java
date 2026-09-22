package id.ac.polinema.lumajang.portalku.pengumuman;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PengumumanRepository extends JpaRepository<Pengumuman, Integer> {
    
    @Query("SELECT p FROM Pengumuman p JOIN FETCH p.kategori")
    List<Pengumuman> findAllWithKategori();
}