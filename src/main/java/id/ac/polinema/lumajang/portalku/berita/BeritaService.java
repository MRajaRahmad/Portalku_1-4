
package id.ac.polinema.lumajang.portalku.berita;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.ac.polinema.lumajang.portalku.berita.dto.BeritaRequest;
import id.ac.polinema.lumajang.portalku.berita.dto.BeritaResponse;
import id.ac.polinema.lumajang.portalku.berita.dto.BeritaRingkasResponse;
import id.ac.polinema.lumajang.portalku.pengumuman.Kategori;
import id.ac.polinema.lumajang.portalku.pengumuman.KategoriRepository;
import id.ac.polinema.lumajang.portalku.shared.FieldPengurutanTidakValidException;
import id.ac.polinema.lumajang.portalku.shared.PageResponse;
import id.ac.polinema.lumajang.portalku.shared.SumberDayaTidakDitemukanException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BeritaService {

    private static final List<String> FIELD_SORT_VALID =
            List.of(
                    "id",
                    "judul",
                    "tanggalTerbit",
                    "jumlahDilihat"
            );

    private final BeritaRepository beritaRepository;
    private final KategoriRepository kategoriRepository;

    // =========================================
    // MENAMPILKAN DAFTAR BERITA
    // Fitur pagination P5 tetap dipertahankan
    // =========================================

    public PageResponse<BeritaRingkasResponse> cari(
            int page,
            int size,
            String kategori,
            String cari,
            List<String> sort) {

        if (page < 0) {
            page = 0;
        }

        if (size < 1) {
            size = 10;
        }

        if (size > 50) {
            size = 50;
        }

        String kataKunci = cari == null
                ? ""
                : cari.trim();

        // Validasi pengurutan sebelum diteruskan ke query native.
        buatSort(sort);

        int offset = page * size;

        List<BeritaRingkasResponse> hasil =
                beritaRepository.cariNative(
                        kategori,
                        kataKunci,
                        offset,
                        size,
                        sort
                );

        long totalData =
                beritaRepository.hitungNative(
                        kategori,
                        kataKunci
                );

        Pageable pageable = PageRequest.of(page, size);

        Page<BeritaRingkasResponse> pageResult =
                new PageImpl<>(
                        hasil,
                        pageable,
                        totalData
                );

        return PageResponse.dari(pageResult);
    }

    // =========================================
    // MENAMBAH BERITA
    // =========================================

    @Transactional
    public BeritaResponse tambah(BeritaRequest req) {

        Kategori kategori = cariKategori(req.idKategori());

        Berita berita = new Berita();
        berita.setJudul(req.judul().trim());
        berita.setIsi(req.isi());
        berita.setTanggalTerbit(req.tanggalTerbit());
        berita.setKategori(kategori);

        // jumlahDilihat menggunakan nilai awal dari entity.
        Berita tersimpan = beritaRepository.save(berita);

        return keResponse(tersimpan);
    }

    // =========================================
    // MENGUBAH BERITA
    // =========================================

    @Transactional
    public BeritaResponse ubah(
            Long id,
            BeritaRequest req) {

        Berita berita = cariBerita(id);
        Kategori kategori = cariKategori(req.idKategori());

        berita.setJudul(req.judul().trim());
        berita.setIsi(req.isi());
        berita.setTanggalTerbit(req.tanggalTerbit());
        berita.setKategori(kategori);

        Berita tersimpan = beritaRepository.save(berita);

        return keResponse(tersimpan);
    }

    // =========================================
    // MENGHAPUS BERITA
    // =========================================

    @Transactional
    public void hapus(Long id) {

        Berita berita = cariBerita(id);

        beritaRepository.delete(berita);
    }

    // =========================================
    // MENAMBAH JUMLAH DILIHAT
    // Fitur P5 tetap dipertahankan
    // =========================================

    @Transactional
    public void tambahJumlahDilihat(Long id) {
        beritaRepository.tambahJumlahDilihat(id);
    }

    // =========================================
    // MENCARI BERITA BERDASARKAN ID
    // =========================================

    private Berita cariBerita(Long id) {

        return beritaRepository.findById(id)
                .orElseThrow(() ->
                        new SumberDayaTidakDitemukanException(
                                "Berita",
                                id
                        )
                );
    }

    // =========================================
    // MENCARI KATEGORI BERDASARKAN ID
    // =========================================

    private Kategori cariKategori(Long id) {

        return kategoriRepository.findById(id)
                .orElseThrow(() ->
                        new SumberDayaTidakDitemukanException(
                                "Kategori",
                                id
                        )
                );
    }

    // =========================================
    // ENTITY MENJADI DTO RESPONS
    // =========================================

    private BeritaResponse keResponse(Berita berita) {

        return new BeritaResponse(
                berita.getId(),
                berita.getJudul(),
                berita.getIsi(),
                berita.getTanggalTerbit(),
                berita.getJumlahDilihat(),
                berita.getKategori().getNama()
        );
    }

    // =========================================
    // VALIDASI PENGURUTAN
    // Fitur P5 tetap dipertahankan
    // =========================================

    private Sort buatSort(List<String> sort) {

        if (sort == null || sort.isEmpty()) {
            return Sort.by(
                    Sort.Order.desc("tanggalTerbit"),
                    Sort.Order.asc("id")
            );
        }

        Sort hasil = Sort.unsorted();

        for (String nilai : sort) {

            String[] bagian = nilai.split(",");

            String field = bagian[0];

            if (!FIELD_SORT_VALID.contains(field)) {
                throw new FieldPengurutanTidakValidException(field);
            }

            Sort.Direction arah =
                    bagian.length > 1
                            ? Sort.Direction.fromString(bagian[1])
                            : Sort.Direction.ASC;

            Sort.Order order = new Sort.Order(arah, field);

            hasil = hasil.and(Sort.by(order));
        }

        return hasil;
    }
}
