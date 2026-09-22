package id.ac.polinema.lumajang.portalku.pengumuman;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PengumumanService {

    private final PengumumanRepository pengumumanRepository;

    public PengumumanService(PengumumanRepository pengumumanRepository) {
        this.pengumumanRepository = pengumumanRepository;
    }

    public List<PengumumanResponse> getAllPengumuman() {
        return pengumumanRepository.findAllWithKategori().stream()
                .map(PengumumanResponse::new)
                .collect(Collectors.toList());
    }

    public PengumumanResponse tambahPengumuman(Pengumuman pengumuman) {
        Pengumuman saved = pengumumanRepository.save(pengumuman);
        return new PengumumanResponse(saved);
    }

    public void hapusPengumuman(Integer id) {
        pengumumanRepository.deleteById(id);
    }
}