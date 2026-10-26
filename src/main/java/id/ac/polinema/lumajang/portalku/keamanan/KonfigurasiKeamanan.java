
package id.ac.polinema.lumajang.portalku.keamanan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class KonfigurasiKeamanan {

    private final LayananDetailPengguna layananDetailPengguna;
    private final TitikMasukTanpaAutentikasi titikMasukTanpaAutentikasi;
    private final PenanganAksesDitolak penanganAksesDitolak;
    private final FilterJwt filterJwt;

    @Bean
    public PasswordEncoder pengkodeSandi() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager pengelolaAutentikasi(
            AuthenticationConfiguration konfigurasi) throws Exception {
        return konfigurasi.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain rantaiFilter(
            HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(
                                titikMasukTanpaAutentikasi)
                        .accessDeniedHandler(
                                penanganAksesDitolak))
                .authorizeHttpRequests(auth -> auth
                        // Endpoint autentikasi boleh diakses publik
                        .requestMatchers("/api/auth/**").permitAll()

                        // Swagger dan dokumentasi API
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**").permitAll()

                        // Membaca pengumuman boleh tanpa login
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/pengumuman/**").permitAll()

                        // Membaca berita boleh tanpa login
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/berita/**").permitAll()

                        // Menambah jumlah dilihat boleh tanpa login
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/pengumuman/*/dilihat").permitAll()

                        // Pengelolaan akun hanya untuk ADMIN
                        .requestMatchers(
                                "/api/pengguna/**").hasRole("ADMIN")

                        // Endpoint lainnya harus terautentikasi
                        .anyRequest().authenticated())
                .addFilterBefore(
                        filterJwt,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
