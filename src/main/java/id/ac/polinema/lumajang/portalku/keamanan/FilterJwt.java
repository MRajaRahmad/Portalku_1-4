
package id.ac.polinema.lumajang.portalku.keamanan;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FilterJwt extends OncePerRequestFilter {

    private final LayananJwt layananJwt;
    private final LayananDetailPengguna layananDetailPengguna;

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain)
            throws ServletException, IOException {

        String header = req.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            try {
                String token = header.substring(7);
                String email = layananJwt.bacaSubjek(token);

                if (SecurityContextHolder.getContext()
                        .getAuthentication() == null) {

                    UserDetails detail =
                            layananDetailPengguna
                                    .loadUserByUsername(email);

                    var auth =
                            new UsernamePasswordAuthenticationToken(
                                    detail,
                                    null,
                                    detail.getAuthorities());

                    SecurityContextHolder.getContext()
                            .setAuthentication(auth);
                }

            } catch (JwtException | UsernameNotFoundException ex) {
                // Token tidak sah: kosongkan konteks keamanan.
                // AuthenticationEntryPoint akan menangani permintaan
                // yang membutuhkan autentikasi.
                SecurityContextHolder.clearContext();
            }
        }

        // Wajib: teruskan permintaan ke filter berikutnya.
        chain.doFilter(req, res);
    }
}
