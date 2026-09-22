package id.ac.polinema.lumajang.portalku.pengumuman;

import jakarta.persistence.*;

@Entity
@Table(name = "lampiran")
public class Lampiran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_berkas", nullable = false)
    private String namaBerkas;

    @Column(nullable = false)
    private String ukuran;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pengumuman_id", nullable = false)
    private Pengumuman pengumuman;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNamaBerkas() { return namaBerkas; }
    public void setNamaBerkas(String namaBerkas) { this.namaBerkas = namaBerkas; }

    public String getUkuran() { return ukuran; }
    public void setUkuran(String ukuran) { this.ukuran = ukuran; }

    public Pengumuman getPengumuman() { return pengumuman; }
    public void setPengumuman(Pengumuman penguman) { this.pengumuman = penguman; }
}