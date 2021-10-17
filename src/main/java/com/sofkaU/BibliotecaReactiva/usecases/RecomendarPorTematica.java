package com.sofkaU.BibliotecaReactiva.usecases;

import com.sofkaU.BibliotecaReactiva.models.RecursoDto;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface RecomendarPorTematica {
    Flux<RecursoDto> get(@Validated String tematicaRecurso);
}
