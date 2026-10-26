package id.ac.polinema.lumajang.portalku.berita;

import java.time.LocalDateTime;

import id.ac.polinema.lumajang.portalku.pengumuman.Kategori;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "berita")
@Getter
@Setter
@NoArgsConstructor
public class Berita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String judul;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String isi;

    @Column(name = "tanggal_terbit", nullable = false)
    private LocalDateTime tanggalTerbit;

    @Column(name = "jumlah_dilihat", nullable = false)
    private Integer jumlahDilihat = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kategori_id", nullable = false)
    private Kategori kategori;
}