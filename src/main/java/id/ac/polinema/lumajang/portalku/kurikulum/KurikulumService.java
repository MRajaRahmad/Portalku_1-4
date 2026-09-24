package id.ac.polinema.lumajang.portalku.kurikulum;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.ac.polinema.lumajang.portalku.kurikulum.dto.KurikulumRequest;
import id.ac.polinema.lumajang.portalku.kurikulum.dto.KurikulumResponse;
import id.ac.polinema.lumajang.portalku.prodi.Prodi;
import id.ac.polinema.lumajang.portalku.prodi.ProdiRepository;
import id.ac.polinema.lumajang.portalku.shared.KurikulumTidakDitemukanException;
import id.ac.polinema.lumajang.portalku.shared.ProdiTidakDitemukanException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KurikulumService {

    private final KurikulumRepository kurikulumRepository;
    private final ProdiRepository prodiRepository;
    private final KurikulumMapper mapper;

    public List<KurikulumResponse> cariSemua() {

        return kurikulumRepository.findAllWithProdi()
                .stream()
                .map(mapper::keResponse)
                .toList();
    }

    public KurikulumResponse cariSatu(Integer id) {

        return mapper.keResponse(
                ambilAtauGagal(id)
        );
    }

    @Transactional
    public KurikulumResponse tambah(
            KurikulumRequest req) {

        Prodi prodi =
                prodiRepository.findById(req.idProdi())
                        .orElseThrow(() ->
                                new ProdiTidakDitemukanException(
                                        req.idProdi()
                                ));

        Kurikulum kurikulum =
                mapper.keEntity(req, prodi);

        return mapper.keResponse(
                kurikulumRepository.save(kurikulum)
        );
    }

    @Transactional
    public KurikulumResponse ubah(
            Integer id,
            KurikulumRequest req) {

        Kurikulum kurikulum =
                ambilAtauGagal(id);

        Prodi prodi =
                prodiRepository.findById(req.idProdi())
                        .orElseThrow(() ->
                                new ProdiTidakDitemukanException(
                                        req.idProdi()
                                ));

        mapper.terapkan(
                req,
                kurikulum,
                prodi
        );

        return mapper.keResponse(kurikulum);
    }

    @Transactional
    public void hapus(Integer id) {

        kurikulumRepository.delete(
                ambilAtauGagal(id)
        );
    }

    private Kurikulum ambilAtauGagal(Integer id) {

        return kurikulumRepository.findById(id)
                .orElseThrow(() ->
                        new KurikulumTidakDitemukanException(id)
                );
    }
}