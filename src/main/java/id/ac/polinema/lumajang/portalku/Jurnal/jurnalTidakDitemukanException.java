package id.ac.polinema.lumajang.portalku.Jurnal;

public class jurnalTidakDitemukanException extends RuntimeException {

    public jurnalTidakDitemukanException(Integer id) {
        super("Jurnal dengan id " + id + " tidak ditemukan");
    }
}