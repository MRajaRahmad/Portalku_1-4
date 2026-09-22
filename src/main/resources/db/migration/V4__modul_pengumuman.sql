-- Tabel Kategori
CREATE TABLE kategori (
    id BIGSERIAL PRIMARY KEY,
    nama VARCHAR(100) NOT NULL UNIQUE
);

-- Tabel Pengumuman
CREATE TABLE pengumuman (
    id BIGSERIAL PRIMARY KEY,
    judul VARCHAR(255) NOT NULL UNIQUE,
    isi TEXT NOT NULL,
    tanggal_terbit TIMESTAMP NOT NULL,
    jumlah_dilihat INT DEFAULT 0,
    kategori_id BIGINT NOT NULL,
    CONSTRAINT fk_pengumuman_kategori FOREIGN KEY (kategori_id) REFERENCES kategori(id)
);

-- Tabel Lampiran (Weak Entity dengan Cascade Delete)
CREATE TABLE lampiran (
    id BIGSERIAL PRIMARY KEY,
    nama_berkas VARCHAR(255) NOT NULL,
    ukuran VARCHAR(50) NOT NULL,
    pengumuman_id BIGINT NOT NULL,
    CONSTRAINT fk_lampiran_pengumuman FOREIGN KEY (pengumuman_id) REFERENCES pengumuman(id) ON DELETE CASCADE
);

-- Tabel Relasi Many-to-Many Pengumuman dan Prodi
CREATE TABLE pengumuman_prodi (
    pengumuman_id BIGINT NOT NULL,
    prodi_id BIGINT NOT NULL,
    PRIMARY KEY (pengumuman_id, prodi_id),
    CONSTRAINT fk_pp_pengumuman FOREIGN KEY (pengumuman_id) REFERENCES pengumuman(id) ON DELETE CASCADE,
    CONSTRAINT fk_pp_prodi FOREIGN KEY (prodi_id) REFERENCES program_studi(id) ON DELETE CASCADE
);