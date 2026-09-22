package id.ac.polinema.lumajang.portalku.pengumuman;

public class PengumumanResponse {
    private Long id;
    private String judul;
    private String isi;
    private String namaKategori;
    private int jumlahLampiran;

    public PengumumanResponse(Pengumuman pengumuman) {
        this.id = pengumuman.getId();
        this.judul = pengumuman.getJudul();
        this.isi = pengumuman.getIsi();
        
        if (pengumuman.getKategori() != null) {
            this.namaKategori = pengumuman.getKategori().getNama();
        }
        
        // Atur jumlah lampiran menjadi 0 atau sesuaikan dengan method di Pengumuman.java jika sudah ada
        this.jumlahLampiran = 0;
    }

    // Getters
    public Long getId() { return id; }
    public String getJudul() { return judul; }
    public String getIsi() { return isi; }
    public String getNamaKategori() { return namaKategori; }
    public int getJumlahLampiran() { return jumlahLampiran; }
}