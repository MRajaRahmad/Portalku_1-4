
package id.ac.polinema.lumajang.portalku.keamanan.dto;

import id.ac.polinema.lumajang.portalku.keamanan.Pengguna;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PermintaanPengguna(

        @NotBlank(message = "Email wajib diisi")
        @Email(message = "Format email tidak sah")
        String email,

        @NotBlank(message = "Nama wajib diisi")
        @Size(max = 150, message = "Nama maksimal 150 karakter")
        String nama,

        @NotBlank(message = "Kata sandi wajib diisi")
        @Size(min = 12, message = "Kata sandi minimal 12 karakter")
        String sandi,

        @NotNull(message = "Peran wajib dipilih")
        Pengguna.Peran peran

) {
}
