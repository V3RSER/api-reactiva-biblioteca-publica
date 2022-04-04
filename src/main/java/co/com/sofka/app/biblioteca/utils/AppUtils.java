package co.com.sofka.app.biblioteca.utils;

import co.com.sofka.app.biblioteca.dtos.RecursoDTO;
import co.com.sofka.app.biblioteca.models.Recurso;
import org.springframework.beans.BeanUtils;

public class AppUtils {
    public static RecursoDTO recursoModelToDto(Recurso recurso) {
        RecursoDTO recursoDTO = new RecursoDTO();
        BeanUtils.copyProperties(recurso, recursoDTO);
        return recursoDTO;
    }

    public static Recurso recursoDtoToModel(RecursoDTO recursoDTO) {
        Recurso recurso = new Recurso();
        BeanUtils.copyProperties(recursoDTO, recurso);
        return recurso;
    }
}
