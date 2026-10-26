
package id.ac.polinema.lumajang.portalku.keamanan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class InisialisasiPenggunaDev implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(InisialisasiPenggunaDev.class);

    private final PenggunaRepository penggunaRepository;
    private final PasswordEncoder pengkodeSandi;

    @Value("${ADMIN_EMAIL:}")
    private String emailAdmin;

    @Value("${ADMIN_PASSWORD:}")
    private String sandiAdmin;

    @Value("${ADMIN_NAME:Admin Portalku}")
    private String namaAdmin;

    @Value("${EDITOR_EMAIL:}")
    private String emailEditor;

    @Value("${EDITOR_PASSWORD:}")
    private String sandiEditor;

    @Value("${EDITOR_NAME:Editor Portalku}")
    private String namaEditor;

    @Override
    @Transactional
    public void run(String... args) {

        // Inisialisasi akun ADMIN development
        buatAkunJikaBelumAda(
                emailAdmin,
                sandiAdmin,
                namaAdmin,
                Pengguna.Peran.ADMIN
        );

        // Inisialisasi akun EDITOR development
        buatAkunJikaBelumAda(
                emailEditor,
                sandiEditor,
                namaEditor,
                Pengguna.Peran.EDITOR
        );
    }

    private void buatAkunJikaBelumAda(
            String email,
            String sandi,
            String nama,
            Pengguna.Peran peran) {

        if (email == null || email.isBlank()
                || sandi == null || sandi.isBlank()) {

            log.warn(
                    "Email atau kata sandi untuk peran {} belum disetel. "
                    + "Akun development tidak dibuat.",
                    peran
            );
            return;
        }

        String emailBersih = email.trim();

        var akunAda =
                penggunaRepository.findByEmailAndAktifTrue(emailBersih);

        if (akunAda.isPresent()) {

            if (akunAda.get().getPeran() != peran) {
                log.warn(
                        "Email {} sudah terdaftar dengan peran {}. "
                        + "Akun tidak diubah menjadi {}.",
                        emailBersih,
                        akunAda.get().getPeran(),
                        peran
                );
            } else {
                log.info(
                        "Akun {} development sudah tersedia.",
                        peran
                );
            }

            return;
        }

        Pengguna pengguna = new Pengguna();
        pengguna.setEmail(emailBersih);
        pengguna.setSandi(pengkodeSandi.encode(sandi));
        pengguna.setNama(
                nama == null || nama.isBlank()
                        ? peran.name()
                        : nama.trim()
        );
        pengguna.setPeran(peran);
        pengguna.setAktif(true);

        penggunaRepository.save(pengguna);

        log.info(
                "Akun {} development berhasil dibuat.",
                peran
        );
    }
}
