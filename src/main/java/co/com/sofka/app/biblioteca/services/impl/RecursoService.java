package co.com.sofka.app.biblioteca.services.impl;

import co.com.sofka.app.biblioteca.dtos.RecursoDTO;
import co.com.sofka.app.biblioteca.models.Recurso;
import co.com.sofka.app.biblioteca.repositories.RecursoRepository;
import co.com.sofka.app.biblioteca.services.iRecursoService;
import co.com.sofka.app.biblioteca.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class RecursoService implements iRecursoService {
    @Autowired
    private RecursoRepository repository;

    @Override
    public Mono<RecursoDTO> add(Mono<RecursoDTO> recursoDTO) {
        return recursoDTO.map(AppUtils::recursoDtoToModel)
                .flatMap(this.repository::save)
                .map(AppUtils::recursoModelToDto);
    }

    @Override
    public Mono<RecursoDTO> delete(String id) {
        return this.repository
                .findById(id)
                .flatMap(p -> this.repository.deleteById(p.getId()).thenReturn(p))
                .map(AppUtils::recursoModelToDto)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<RecursoDTO> update(String id, Mono<RecursoDTO> recursoDTO) {
        return this.repository.findById(id)
                .flatMap(p -> recursoDTO.map(AppUtils::recursoDtoToModel)
                        .doOnNext(recurso -> recurso.setId(id)))
                .flatMap(this.repository::save)
                .map(AppUtils::recursoModelToDto)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<String> lend(String id) {
        AtomicBoolean prestado = new AtomicBoolean(false);
        return this.repository.findById(id)
                .map((Recurso recurso) -> {
                    if (recurso.getDisponibe()) {
                        recurso.setDisponibe(false);
                        recurso.setFechaPrestamo(LocalDateTime.now());
                        prestado.set(true);
                        return recurso;
                    }
                    return recurso;
                })
                .flatMap(this.repository::save)
                .map(recurso -> prestado.get() ?
                        "El recurso ha sido prestado con éxito" :
                        "El recurso no se encuentra disponible")
                .switchIfEmpty(Mono.just("El recurso no existe"));
    }

    @Override
    public Mono<String> giveBack(String id) {
        AtomicBoolean devuelto = new AtomicBoolean(false);
        return this.repository.findById(id)
                .map((Recurso recurso) -> {
                    if (!recurso.getDisponibe()) {
                        recurso.setDisponibe(true);
                        devuelto.set(true);
                        return recurso;
                    }
                    return recurso;
                })
                .flatMap(this.repository::save)
                .map(recurso -> devuelto.get() ?
                        "El recurso ha sido devuelto con éxito" :
                        "El recurso no se encuentra prestado")
                .switchIfEmpty(Mono.just("El recurso no existe"));
    }

    @Override
    public Mono<RecursoDTO> findById(String id) {
        return this.repository.findById(id)
                .map(AppUtils::recursoModelToDto)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<RecursoDTO> findByTipo(String tipo) {
        return this.repository.findByTipo(tipo)
                .map(AppUtils::recursoModelToDto)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Flux<RecursoDTO> findByArea(String area) {
        return this.repository.findByArea(area)
                .map(AppUtils::recursoModelToDto)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Flux<RecursoDTO> findByTipoAndArea(String tipo, String area) {
        return this.repository.findByTipoAndArea(tipo, area)
                .map(AppUtils::recursoModelToDto)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Flux<RecursoDTO> findAll() {
        return this.repository.findAll()
                .map(AppUtils::recursoModelToDto)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<String> available(String id) {
        return this.repository.findById(id)
                .map(recurso -> recurso.getDisponibe() ?
                        "El recurso se encuentra disponible" :
                        ("El recurso no se encuentra disponible. Fecha del último préstamo: " +
                                DateTimeFormatter
                                        .ofPattern("yyyy/MMMM/dd HH:mm:ss")
                                        .format(recurso.getFechaPrestamo())))
                .switchIfEmpty(Mono.just("El recurso no existe"));
    }
}
