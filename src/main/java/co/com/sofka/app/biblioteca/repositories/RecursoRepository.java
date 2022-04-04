package co.com.sofka.app.biblioteca.repositories;

import co.com.sofka.app.biblioteca.models.Recurso;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RecursoRepository extends ReactiveMongoRepository<Recurso, String> {

    Flux<Recurso> findByTipoIgnoreCase(String area);

    Flux<Recurso> findByAreaIgnoreCase(String area);

    Flux<Recurso> findByTipoAndAreaAllIgnoreCase(String tipo, String area);
}
