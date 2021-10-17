package com.sofkaU.BibliotecaReactiva.repositories;

import com.sofkaU.BibliotecaReactiva.collections.Recurso;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RepositorioRecurso extends ReactiveCrudRepository<Recurso, String> {
    Flux<Recurso> findAllByTipo(final String tipo);
    Flux<Recurso> finAllByTematica(final String tematica);
    Flux<Recurso> findAllByTipoYTematica(final String tipo, final String tematica);
}
