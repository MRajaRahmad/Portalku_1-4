package id.ac.polinema.lumajang.portalku.shared;

public class FieldPengurutanTidakValidException extends RuntimeException {

    public FieldPengurutanTidakValidException(String field) {
        super("Field pengurutan tidak valid: " + field);
    }
}