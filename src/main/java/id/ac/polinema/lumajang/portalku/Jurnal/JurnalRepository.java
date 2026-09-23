package id.ac.polinema.lumajang.portalku.jurnal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JurnalRepository extends JpaRepository<Jurnal, Integer> {

    List<Jurnal> findByPenerbit(String penerbit);

    List<Jurnal> findByJudulContainingIgnoreCase(String kataKunci);
}