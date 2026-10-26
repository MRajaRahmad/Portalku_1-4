package id.ac.polinema.lumajang.portalku.keamanan;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PenggunaRepository
        extends JpaRepository<Pengguna, Integer> {

    Optional<Pengguna> findByEmailAndAktifTrue(String email);
}