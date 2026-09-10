package id.ac.polinema.lumajang.portalku.halo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HaloController {

    @GetMapping("/")
    public String halo() {
        return "Selamat datang di Praktikum Pemrograman Web Lanjut!";
    }
}