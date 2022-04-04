package co.com.sofka.app.biblioteca.services;

import co.com.sofka.app.biblioteca.dtos.RecursoDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface iRecursoService {
    // POST
    Mono<RecursoDTO> add(Mono<RecursoDTO> clienteDTO);

    // DELETE
    Mono<RecursoDTO> delete(String id);

    // PUT
    Mono<RecursoDTO> update(String id, Mono<RecursoDTO> recursoDTO);

    Mono<String> lend(String id);

    Mono<String> giveBack(String id);

    // GET
    Mono<RecursoDTO> findById(String id);

    Flux<RecursoDTO> findByTipo(String tipo);

    Flux<RecursoDTO> findByArea(String area);

    Flux<RecursoDTO> findByTipoAndArea(String tipo);

    Flux<RecursoDTO> findAll();

    Mono<String> available(String id);
}
