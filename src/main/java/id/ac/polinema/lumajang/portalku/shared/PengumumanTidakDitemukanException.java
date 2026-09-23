package id.ac.polinema.lumajang.portalku.shared;

public class PengumumanTidakDitemukanException
        extends SumberDayaTidakDitemukanException {

    public PengumumanTidakDitemukanException(Long id) {
        super("Pengumuman", id);
    }
}