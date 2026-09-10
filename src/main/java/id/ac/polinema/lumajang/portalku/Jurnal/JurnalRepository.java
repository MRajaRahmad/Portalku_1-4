package id.ac.polinema.lumajang.portalku.Jurnal;

import java.util.List;
import java.util.Optional;

public interface JurnalRepository {

    List<jurnal> findAll();

    Optional<jurnal> findById(Integer id);

    jurnal save(jurnal jurnal);

    boolean deleteById(Integer id);
}
