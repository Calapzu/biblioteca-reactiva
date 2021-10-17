package com.sofkaU.BibliotecaReactiva.usecases;

import com.sofkaU.BibliotecaReactiva.models.RecursoDto;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@FunctionalInterface
public interface RecomendarPortTipoYTematica {
    Flux<RecursoDto> get(@Valid String tipo, @Valid String tematica);
}
