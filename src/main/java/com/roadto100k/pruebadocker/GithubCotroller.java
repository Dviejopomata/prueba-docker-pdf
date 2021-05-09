package com.roadto100k.pruebadocker;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/prueba")
public class GithubCotroller {

    private final GithubRepository repository;

    @GetMapping("/{owner}/{repo}/pdf")
    public ResponseEntity<byte[]> getPdf(
            @PathVariable String owner,
            @PathVariable String repo
    ) throws FileNotFoundException, DocumentException {
        System.out.println("Estoy sirviendo el PDF");
        Map<String, Object> repository = this.repository.getRepository(owner, repo);
        final Document document = new Document();
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, os);
        String description = ((String) repository.get("description"));
        String language = ((String) repository.get("language"));
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.RED);
        Paragraph chunk = new Paragraph(description, font);
        Paragraph chunk2 = new Paragraph(language, font);

        document.add(chunk);
        document.add(chunk2);
        document.close();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        System.out.println("Acabo de generar el PDF");
        return new ResponseEntity<>(os.toByteArray(), headers, HttpStatus.OK);
    }

}
// .\mvnw package && java -jar target/prueba-docker-0.0.1-SNAPSHOT.jar