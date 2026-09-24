package id.ac.polinema.lumajang.portalku.pengumuman;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LampiranRepository extends JpaRepository<Lampiran, Long> {

    List<Lampiran> findByPengumumanId(Long pengumumanId);
}