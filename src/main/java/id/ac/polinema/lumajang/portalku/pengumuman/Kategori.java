package id.ac.polinema.lumajang.portalku.pengumuman;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "kategori")
public class Kategori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nama;

    @OneToMany(mappedBy = "kategori", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pengumuman> pengumumans;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public List<Pengumuman> getPengumumans() { return pengumumans; }
    public void setPengumumans(List<Pengumuman> pengumumans) { this.pengumumans = pengumumans; }
}