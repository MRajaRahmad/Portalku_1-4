package id.ac.polinema.lumajang.portalku.Jurnal;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class jurnalService {

    private final JurnalRepository jurnalRepository;

    public List<jurnal> cariSemua() {
        return jurnalRepository.findAll();
    }

    public jurnal cariSatu(Integer id) {
        return jurnalRepository.findById(id)
                .orElseThrow(() -> new jurnalTidakDitemukanException(id));
    }

    public jurnal tambah(jurnal jurnal) {
        jurnal.setId(null);         // id selalu ditentukan oleh penyimpanan
        return jurnalRepository.save(jurnal);
    }

    // SESUDAH — memakai existsById untuk memeriksa lebih dahulu
public void hapus(Integer id) {
    if (!jurnalRepository.existsById(id)) {
        throw new jurnalTidakDitemukanException(id);
    }
    jurnalRepository.deleteById(id);
}
}