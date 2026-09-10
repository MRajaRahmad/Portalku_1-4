package id.ac.polinema.lumajang.portalku.Jurnal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class jurnal {

    private Integer id;
    private String judul;
    private String penerbit;
    private Integer tahunTerbit;
}