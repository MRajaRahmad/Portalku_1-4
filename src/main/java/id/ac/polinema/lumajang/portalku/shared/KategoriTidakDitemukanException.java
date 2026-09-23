package id.ac.polinema.lumajang.portalku.shared;

public class KategoriTidakDitemukanException
        extends SumberDayaTidakDitemukanException {

    public KategoriTidakDitemukanException(Long id) {
        super("Kategori", id);
    }
}