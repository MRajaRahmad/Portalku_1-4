package id.ac.polinema.lumajang.portalku.pengumuman;

import org.springframework.stereotype.Component;

import id.ac.polinema.lumajang.portalku.pengumuman.dto.LampiranRequest;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.LampiranResponse;

@Component
public class LampiranMapper {

    public LampiranResponse keResponse(Lampiran lampiran) {
        return new LampiranResponse(
                lampiran.getId(),
                lampiran.getNamaBerkas(),
                lampiran.getUkuran()
        );
    }

    public Lampiran keEntity(LampiranRequest req, Pengumuman pengumuman) {
        Lampiran lampiran = new Lampiran();
        lampiran.setNamaBerkas(req.namaBerkas());
        lampiran.setUkuran(req.ukuran());
        lampiran.setPengumuman(pengumuman);
        return lampiran;
    }
}