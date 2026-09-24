package id.ac.polinema.lumajang.portalku.kurikulum.dto;

import id.ac.polinema.lumajang.portalku.kurikulum.Kurikulum.Status;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record KurikulumRequest(

        @NotBlank(message = "Kode kurikulum wajib diisi")
        @Size(max = 25, message = "Kode kurikulum maksimal 25 karakter")
        String kode,

        @NotNull(message = "Tahun berlaku wajib diisi")
        @Min(value = 2000, message = "Tahun berlaku minimal 2000")
        @Max(value = 2100, message = "Tahun berlaku maksimal 2100")
        Integer tahunBerlaku,

        @NotNull(message = "Status wajib diisi")
        Status status,

        @NotNull(message = "Prodi wajib dipilih")
        Integer idProdi
) {}