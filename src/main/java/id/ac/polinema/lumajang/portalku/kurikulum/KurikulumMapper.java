package id.ac.polinema.lumajang.portalku.kurikulum;

import org.springframework.stereotype.Component;

import id.ac.polinema.lumajang.portalku.kurikulum.dto.KurikulumRequest;
import id.ac.polinema.lumajang.portalku.kurikulum.dto.KurikulumResponse;
import id.ac.polinema.lumajang.portalku.prodi.Prodi;

@Component
public class KurikulumMapper {

    public KurikulumResponse keResponse(Kurikulum kurikulum) {

        return new KurikulumResponse(
                kurikulum.getId(),
                kurikulum.getKode(),
                kurikulum.getTahunBerlaku(),
                kurikulum.getStatus(),
                kurikulum.getProdi() != null
                        ? kurikulum.getProdi().getId()
                        : null,
                kurikulum.getProdi() != null
                        ? kurikulum.getProdi().getNama()
                        : null
        );
    }

    public Kurikulum keEntity(
            KurikulumRequest req,
            Prodi prodi) {

        Kurikulum kurikulum = new Kurikulum();

        kurikulum.setKode(req.kode());
        kurikulum.setTahunBerlaku(req.tahunBerlaku());
        kurikulum.setStatus(req.status());
        kurikulum.setProdi(prodi);

        return kurikulum;
    }

    public void terapkan(
            KurikulumRequest req,
            Kurikulum kurikulum,
            Prodi prodi) {

        kurikulum.setKode(req.kode());
        kurikulum.setTahunBerlaku(req.tahunBerlaku());
        kurikulum.setStatus(req.status());
        kurikulum.setProdi(prodi);
    }
}