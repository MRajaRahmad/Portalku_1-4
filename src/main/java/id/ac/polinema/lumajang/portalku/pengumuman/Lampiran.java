package id.ac.polinema.lumajang.portalku.pengumuman;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lampiran")
@Getter
@Setter
@NoArgsConstructor
public class Lampiran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_berkas", nullable = false, length = 255)
    private String namaBerkas;

    @Column(nullable = false, length = 50)
    private String ukuran;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pengumuman_id", nullable = false)
    private Pengumuman pengumuman;
}