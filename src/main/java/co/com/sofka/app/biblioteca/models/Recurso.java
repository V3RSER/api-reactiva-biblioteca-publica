package co.com.sofka.app.biblioteca.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "recursos")
public class Recurso {
    @Id
    private String id;
    private String nombre;
    private String tipo;
    private String area;
    private Boolean disponible;
    private LocalDateTime fechaPrestamo;
}
