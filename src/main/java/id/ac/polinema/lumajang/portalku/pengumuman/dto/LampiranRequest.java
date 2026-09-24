package id.ac.polinema.lumajang.portalku.pengumuman.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LampiranRequest(

        @NotBlank(message = "Nama berkas wajib diisi")
        @Size(max = 255, message = "Nama berkas maksimal 255 karakter")
        String namaBerkas,

        @NotBlank(message = "Ukuran berkas wajib diisi")
        @Size(max = 50, message = "Ukuran berkas maksimal 50 karakter")
        String ukuran
) {}