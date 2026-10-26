package id.ac.polinema.lumajang.portalku.keamanan;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenPenyegarRepository
        extends JpaRepository<TokenPenyegar, Integer> {

    Optional<TokenPenyegar> findByToken(String token);
}