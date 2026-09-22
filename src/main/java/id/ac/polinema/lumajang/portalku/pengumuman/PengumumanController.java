package id.ac.polinema.lumajang.portalku.pengumuman;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pengumuman")
public class PengumumanController {

    private final PengumumanService pengumumanService;

    public PengumumanController(PengumumanService pengumumanService) {
        this.pengumumanService = pengumumanService;
    }

    @GetMapping
    public List<PengumumanResponse> getAll() {
        return pengumumanService.getAllPengumuman();
    }

    @PostMapping
    public ResponseEntity<PengumumanResponse> create(@RequestBody Pengumuman pengumuman) {
        PengumumanResponse response = pengumumanService.tambahPengumuman(pengumuman);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        pengumumanService.hapusPengumuman(id);
    }
}