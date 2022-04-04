package co.com.sofka.app.biblioteca.controllers;

import co.com.sofka.app.biblioteca.dtos.RecursoDTO;
import co.com.sofka.app.biblioteca.services.iRecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RecursoController {
    @Autowired
    private iRecursoService service;

    // POST
    @PostMapping("/recurso")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RecursoDTO> add(@RequestBody Mono<RecursoDTO> recursoDTO) {
        return this.service.add(recursoDTO);
    }

    // DELETE
    @DeleteMapping("/recurso/{id}")
    public Mono<ResponseEntity<RecursoDTO>> delete(@PathVariable("id") String id) {
        return this.service.delete(id)
                .flatMap(recurso -> Mono.just(ResponseEntity.ok(recurso)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    // PUT
    @PutMapping("/recurso/{id}")
    public Mono<ResponseEntity<RecursoDTO>> update(@PathVariable("id") String id,
                                                   @RequestBody Mono<RecursoDTO> recursoDTO) {
        return this.service.update(id, recursoDTO)
                .flatMap(recurso -> Mono.just(ResponseEntity.ok(recurso)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/recurso/{id}/prestar")
    public Mono<ResponseEntity<String>> lend(@PathVariable("id") String id) {
        return this.service.lend(id)
                .flatMap(recurso -> Mono.just(ResponseEntity.ok(recurso)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/recurso/{id}/devolver")
    public Mono<ResponseEntity<String>> giveBack(@PathVariable("id") String id) {
        return this.service.giveBack(id)
                .flatMap(recurso -> Mono.just(ResponseEntity.ok(recurso)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    // GET
    @GetMapping("/recurso/{id}")
    public Mono<RecursoDTO> findById(@PathVariable("id") String id) {
        return this.service.findById(id);
    }

    @GetMapping("/recurso/tipo/{tipo}")
    public Flux<RecursoDTO> findByTipo(@PathVariable("tipo") String tipo) {
        return this.service.findByTipo(tipo);
    }

    @GetMapping("/recurso/area/{area}")
    public Flux<RecursoDTO> findByArea(@PathVariable("area") String area) {
        return this.service.findByArea(area);
    }

    @GetMapping("/recurso/tipo/{tipo}/area/{area}")
    public Flux<RecursoDTO> findByTipoAndArea(@PathVariable("tipo") String tipo,
                                              @PathVariable("area") String area) {
        return this.service.findByTipoAndArea(tipo);
    }

    @GetMapping("/recursos")
    public Flux<RecursoDTO> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/recurso/{id}/disponible")
    public Mono<Double> available(@PathVariable("id") String id) {
        return this.service.available(id);
    }
}
