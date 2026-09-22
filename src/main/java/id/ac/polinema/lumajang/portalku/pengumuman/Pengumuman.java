package id.ac.polinema.lumajang.portalku.pengumuman;

import id.ac.polinema.lumajang.portalku.prodi.Prodi;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pengumuman")
public class Pengumuman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String judul;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String isi;

    @Column(name = "tanggal_terbit", nullable = false)
    private LocalDateTime tanggalTerbit;

    @Column(name = "jumlah_dilihat")
    private Integer jumlahDilihat = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kategori_id", nullable = false)
    private Kategori kategori;

    @OneToMany(mappedBy = "pengumuman", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lampiran> lampirans;

    @ManyToMany
    @JoinTable(
        name = "pengumuman_prodi",
        joinColumns = @JoinColumn(name = "pengumuman_id"),
        inverseJoinColumns = @JoinColumn(name = "prodi_id")
    )
    private List<Prodi> prodis;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getIsi() { return isi; }
    public void setIsi(String isi) { this.isi = isi; }

    public LocalDateTime getTanggalTerbit() { return tanggalTerbit; }
    public void setTanggalTerbit(LocalDateTime tanggalTerbit) { this.tanggalTerbit = tanggalTerbit; }

    public Integer getJumlahDilihat() { return jumlahDilihat; }
    public void setJumlahDilihat(Integer jumlahDilihat) { this.jumlahDilihat = jumlahDilihat; }

    public Kategori getKategori() { return kategori; }
    public void setKategori(Kategori kategori) { this.kategori = kategori; }

    public List<Lampiran> getLampirans() { return lampirans; }
    public void setLampirans(List<Lampiran> lampirans) { this.lampirans = lampirans; }

    public List<Prodi> getProdis() { return prodis; }
    public void setProdis(List<Prodi> prodis) { this.prodis = prodis; }
}