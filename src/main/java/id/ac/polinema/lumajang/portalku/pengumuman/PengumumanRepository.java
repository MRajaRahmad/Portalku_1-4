package id.ac.polinema.lumajang.portalku.pengumuman;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PengumumanRepository extends JpaRepository<Pengumuman, Long> {
}