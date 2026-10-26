
package id.ac.polinema.lumajang.portalku.shared;

import java.net.URI;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class PenanganGalatGlobal {

    private static final Logger log =
            LoggerFactory.getLogger(PenanganGalatGlobal.class);

    private static final String DASAR =
            "https://api.psdku.polinema.ac.id/galat/";

    // =========================================
    // 404 - SUMBER DAYA TIDAK DITEMUKAN
    // =========================================

    @ExceptionHandler(SumberDayaTidakDitemukanException.class)
    public ProblemDetail tidakDitemukan(
            SumberDayaTidakDitemukanException ex) {

        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage());

        p.setTitle("Sumber daya tidak ditemukan");
        p.setType(URI.create(DASAR + "tidak-ditemukan"));
        p.setProperty("waktu", Instant.now());

        return p;
    }

    // =========================================
    // 422 - VALIDASI DATA GAGAL
    // =========================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validasiGagal(
            MethodArgumentNotValidException ex) {

        Map<String, String> kesalahan =
                new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(e ->
                        kesalahan.put(
                                e.getField(),
                                e.getDefaultMessage()));

        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Terdapat "
                        + kesalahan.size()
                        + " kesalahan pada data yang dikirim");

        p.setTitle("Data yang dikirim tidak valid");
        p.setType(URI.create(DASAR + "validasi"));
        p.setProperty("waktu", Instant.now());
        p.setProperty("kesalahan", kesalahan);

        return p;
    }

    // =========================================
    // 400 - BADAN PERMINTAAN TIDAK TERBACA
    // =========================================

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail badanTidakTerbaca(
            HttpMessageNotReadableException ex) {

        log.warn(
                "Badan permintaan tidak terbaca: {}",
                ex.getMessage());

        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Badan permintaan tidak dapat dibaca. "
                        + "Pastikan formatnya JSON yang benar.");

        p.setTitle("Permintaan tidak dapat dipahami");
        p.setType(URI.create(DASAR + "permintaan-salah"));
        p.setProperty("waktu", Instant.now());

        return p;
    }

    // =========================================
    // 400 - TIPE PARAMETER TIDAK COCOK
    // =========================================

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail tipeTidakCocok(
            MethodArgumentTypeMismatchException ex) {

        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Nilai '"
                        + ex.getValue()
                        + "' tidak sesuai untuk parameter '"
                        + ex.getName()
                        + "'");

        p.setTitle("Parameter tidak sesuai");
        p.setType(URI.create(DASAR + "permintaan-salah"));
        p.setProperty("waktu", Instant.now());

        return p;
    }

    // =========================================
    // 409 - DATA BENTROK
    // =========================================

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail bentrokData(
            DataIntegrityViolationException ex) {

        log.warn(
                "Pelanggaran batasan basis data",
                ex);

        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Data yang dikirim bentrok dengan data "
                        + "yang sudah ada. "
                        + "Periksa nilai yang harus unik.");

        p.setTitle("Data bentrok");
        p.setType(URI.create(DASAR + "bentrok"));
        p.setProperty("waktu", Instant.now());

        return p;
    }

    // =========================================
    // 400 - FIELD PENGURUTAN TIDAK VALID
    // =========================================

    @ExceptionHandler(FieldPengurutanTidakValidException.class)
    public ProblemDetail fieldPengurutanTidakValid(
            FieldPengurutanTidakValidException ex) {

        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());

        p.setTitle("Field pengurutan tidak valid");
        p.setType(URI.create(DASAR + "permintaan-salah"));
        p.setProperty("waktu", Instant.now());

        return p;
    }

    // =========================================
    // 401 - AUTENTIKASI GAGAL
    // =========================================
    // Mencakup BadCredentialsException.
    // Pesan dibuat generik agar tidak membocorkan
    // apakah email atau kata sandi yang salah.

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail autentikasiGagal(
            AuthenticationException ex) {

        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                "Email atau kata sandi tidak benar.");

        p.setTitle("Belum terautentikasi");
        p.setType(URI.create(DASAR + "autentikasi"));
        p.setProperty("waktu", Instant.now());

        return p;
    }

    // =========================================
    // RESPONSE STATUS EXCEPTION
    // Mempertahankan status 401, 403, 404, dan lain-lain
    // =========================================

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail galatDenganStatus(
            ResponseStatusException ex) {

        HttpStatusCode status = ex.getStatusCode();

        String detail = ex.getReason() != null
                ? ex.getReason()
                : "Permintaan tidak dapat diproses.";

        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                status,
                detail);

        if (status.value() == 401) {
            p.setTitle("Belum terautentikasi");
            p.setType(URI.create(DASAR + "autentikasi"));

        } else if (status.value() == 403) {
            p.setTitle("Akses ditolak");
            p.setType(URI.create(DASAR + "akses-ditolak"));

        } else {
            p.setTitle("Permintaan ditolak");
            p.setType(URI.create(DASAR + "permintaan-ditolak"));
        }

        p.setProperty("waktu", Instant.now());

        return p;
    }

    // =========================================
    // 403 - AKSES DITOLAK
    // =========================================

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail aksesDitolak(
            AccessDeniedException ex) {

        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                "Anda tidak memiliki izin untuk melakukan operasi ini.");

        p.setTitle("Akses ditolak");
        p.setType(URI.create(DASAR + "akses-ditolak"));
        p.setProperty("waktu", Instant.now());

        return p;
    }

    // =========================================
    // 500 - GALAT TAK TERDUGA
    // Handler umum harus berada paling akhir
    // =========================================

    @ExceptionHandler(Exception.class)
    public ProblemDetail galatTakTerduga(
            Exception ex) {

        log.error("Galat tak terduga", ex);

        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Terjadi kesalahan di sisi server. "
                        + "Silakan hubungi pengelola.");

        p.setTitle("Kesalahan server");
        p.setType(URI.create(DASAR + "kesalahan-server"));
        p.setProperty("waktu", Instant.now());

        return p;
    }
}
