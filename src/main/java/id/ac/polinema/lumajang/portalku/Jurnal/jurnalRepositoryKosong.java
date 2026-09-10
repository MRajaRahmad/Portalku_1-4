package id.ac.polinema.lumajang.portalku.Jurnal;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class jurnalRepositoryKosong implements JurnalRepository {

    @Override
    public List<jurnal> findAll() {
        return List.of();
    }

    @Override
    public Optional<jurnal> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public jurnal save(jurnal jurnal) {
        jurnal.setId(0);
        return jurnal;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }
}