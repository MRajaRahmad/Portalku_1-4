package id.ac.polinema.lumajang.portalku.pengumuman;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanRequest;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanResponse;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanRingkasResponse;
import id.ac.polinema.lumajang.portalku.shared.KategoriTidakDitemukanException;
import id.ac.polinema.lumajang.portalku.shared.PengumumanTidakDitemukanException;

@Service
@Transactional(readOnly = true)
public class PengumumanService {

    private final PengumumanRepository pengumumanRepository;
    private final KategoriRepository kategoriRepository;
    private final PengumumanMapper mapper;

    public PengumumanService(
            PengumumanRepository pengumumanRepository,
            KategoriRepository kategoriRepository,
            PengumumanMapper mapper) {

        this.pengumumanRepository = pengumumanRepository;
        this.kategoriRepository = kategoriRepository;
        this.mapper = mapper;
    }

    public List<PengumumanRingkasResponse> cariSemua() {
        return pengumumanRepository.findAllWithKategori()
                .stream()
                .map(mapper::keRingkas)
                .toList();
    }

    public PengumumanResponse cariSatu(Long id) {
        return mapper.keResponse(ambilAtauGagal(id));
    }

    @Transactional
    public PengumumanResponse tambah(PengumumanRequest req) {

        Kategori kategori = kategoriRepository.findById(req.idKategori())
                .orElseThrow(() ->
                        new KategoriTidakDitemukanException(req.idKategori()));

        Pengumuman baru = mapper.keEntity(req, kategori);

        return mapper.keResponse(
                pengumumanRepository.save(baru)
        );
    }

    @Transactional
    public PengumumanResponse ubah(
            Long id,
            PengumumanRequest req) {

        Pengumuman p = ambilAtauGagal(id);

        Kategori kategori = kategoriRepository.findById(req.idKategori())
                .orElseThrow(() ->
                        new KategoriTidakDitemukanException(req.idKategori()));

        mapper.terapkan(req, p, kategori);

        return mapper.keResponse(p);
    }

    @Transactional
    public void hapus(Long id) {
        pengumumanRepository.delete(
                ambilAtauGagal(id)
        );
    }

    private Pengumuman ambilAtauGagal(Long id) {
        return pengumumanRepository.findById(id)
                .orElseThrow(() ->
                        new PengumumanTidakDitemukanException(id));
    }
}