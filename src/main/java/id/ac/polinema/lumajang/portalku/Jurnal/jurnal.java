package id.ac.polinema.lumajang.portalku.Jurnal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jurnal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class jurnal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String judul;

    @Column(nullable = false, length = 100)
    private String penerbit;

    @Column(name = "tahun_terbit", nullable = false)
    private Integer tahunTerbit;

    private String issn;

// Tambahkan getter dan setter untuk issn (atau sesuaikan dengan anotasi Lombok jika menggunakan Lombok)
public String getIssn() {
    return issn;
}

public void setIssn(String issn) {
    this.issn = issn;
}
}
