package co.com.sofka.app.biblioteca.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecursoDTO {
    private String id;
    private String nombre;
    private String tipo;
    private String area;
    private Boolean disponible;
    private LocalDateTime fechaPrestamo;
}
