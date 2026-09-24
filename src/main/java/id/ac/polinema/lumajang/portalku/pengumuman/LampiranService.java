package id.ac.polinema.lumajang.portalku.pengumuman;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import id.ac.polinema.lumajang.portalku.pengumuman.dto.LampiranRequest;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.LampiranResponse;
import id.ac.polinema.lumajang.portalku.shared.PengumumanTidakDitemukanException;

@Service
@RequiredArgsConstructor
public class LampiranService {

    private final LampiranRepository lampiranRepository;
    private final PengumumanRepository pengumumanRepository;
    private final LampiranMapper lampiranMapper;

    @Transactional(readOnly = true)
    public List<LampiranResponse> cariSemua(Long pengumumanId) {

        if (!pengumumanRepository.existsById(pengumumanId)) {
            throw new PengumumanTidakDitemukanException(pengumumanId);
        }

        return lampiranRepository
                .findByPengumumanId(pengumumanId)
                .stream()
                .map(lampiranMapper::keResponse)
                .toList();
    }

    @Transactional
    public LampiranResponse tambah(
            Long pengumumanId,
            LampiranRequest req) {

        Pengumuman pengumuman =
                pengumumanRepository.findById(pengumumanId)
                        .orElseThrow(() ->
                                new PengumumanTidakDitemukanException(
                                        pengumumanId));

        Lampiran lampiran =
                lampiranMapper.keEntity(req, pengumuman);

        Lampiran tersimpan =
                lampiranRepository.save(lampiran);

        return lampiranMapper.keResponse(tersimpan);
    }
}