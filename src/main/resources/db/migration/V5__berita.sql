-- Tabel berita untuk tugas lab P5 bagian 9.1
CREATE TABLE berita (
    id BIGSERIAL PRIMARY KEY,
    judul VARCHAR(255) NOT NULL UNIQUE,
    isi TEXT NOT NULL,
    tanggal_terbit TIMESTAMP NOT NULL,
    jumlah_dilihat INT NOT NULL DEFAULT 0,
    kategori_id BIGINT NOT NULL,

    CONSTRAINT fk_berita_kategori
        FOREIGN KEY (kategori_id)
        REFERENCES kategori(id)
);

-- Pastikan kategori pengujian tersedia.
INSERT INTO kategori (nama)
VALUES
    ('Akademik'),
    ('Kegiatan'),
    ('Informasi')
ON CONFLICT (nama) DO NOTHING;

-- Data contoh agar pagination benar-benar terlihat.
INSERT INTO berita (
    judul,
    isi,
    tanggal_terbit,
    jumlah_dilihat,
    kategori_id
)
SELECT
    'Berita nomor ' || g || ' tentang kegiatan akademik',
    repeat(
        'Isi berita contoh nomor ' || g || '. ',
        40
    ),
    TIMESTAMP '2026-01-01 08:00:00'
        + ((g * 3) % 240) * INTERVAL '1 day',
    (g * 7) % 500,
    CASE
        WHEN (g % 3) = 1
            THEN (SELECT id FROM kategori WHERE nama = 'Akademik')
        WHEN (g % 3) = 2
            THEN (SELECT id FROM kategori WHERE nama = 'Kegiatan')
        ELSE
            (SELECT id FROM kategori WHERE nama = 'Informasi')
    END
FROM generate_series(1, 120) AS g;