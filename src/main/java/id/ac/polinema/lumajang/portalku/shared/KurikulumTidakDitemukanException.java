package id.ac.polinema.lumajang.portalku.shared;

public class KurikulumTidakDitemukanException
        extends SumberDayaTidakDitemukanException {

    public KurikulumTidakDitemukanException(Integer id) {
        super("Kurikulum", id);
    }
}