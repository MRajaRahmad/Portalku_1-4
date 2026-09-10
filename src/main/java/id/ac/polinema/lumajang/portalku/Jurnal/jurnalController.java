package id.ac.polinema.lumajang.portalku.Jurnal;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jurnal")
@RequiredArgsConstructor
public class jurnalController {

    private final jurnalService jurnalService;

    @GetMapping
    public List<jurnal> semua() {
        return jurnalService.cariSemua();
    }

    @GetMapping("/{id}")
    public jurnal satu(@PathVariable Integer id) {
        return jurnalService.cariSatu(id);
    }

    @PostMapping
    public jurnal tambah(@RequestBody jurnal jurnal) {
        return jurnalService.tambah(jurnal);
    }

    @DeleteMapping("/{id}")
    public void hapus(@PathVariable Integer id) {
        jurnalService.hapus(id);
    }
}