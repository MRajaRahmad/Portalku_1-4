package id.ac.polinema.lumajang.portalku.shared;

public class ProdiTidakDitemukanException
        extends SumberDayaTidakDitemukanException {

    public ProdiTidakDitemukanException(Integer id) {
        super("Prodi", id);
    }
}