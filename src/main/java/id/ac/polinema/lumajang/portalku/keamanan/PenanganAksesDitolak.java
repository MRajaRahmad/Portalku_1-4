package id.ac.polinema.lumajang.portalku.keamanan;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PenanganAksesDitolak implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest req,
            HttpServletResponse res,
            AccessDeniedException ex)
            throws IOException, ServletException {

        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                "Anda tidak memiliki izin untuk mengakses sumber daya ini.");

        p.setTitle("Akses ditolak");
        p.setType(URI.create(
                "https://api.psdku.polinema.ac.id/galat/akses-ditolak"));
        p.setProperty("waktu", Instant.now());

        res.setStatus(HttpStatus.FORBIDDEN.value());
        res.setContentType("application/problem+json");

        objectMapper.writeValue(res.getOutputStream(), p);
    }
}