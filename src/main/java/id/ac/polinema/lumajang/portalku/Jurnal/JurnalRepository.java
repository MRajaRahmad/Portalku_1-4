package id.ac.polinema.lumajang.portalku.Jurnal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JurnalRepository extends JpaRepository<jurnal, Integer> {
    
    List<jurnal> findByPenerbit(String penerbit);
    
    List<jurnal> findByJudulContainingIgnoreCase(String kataKunci);
}