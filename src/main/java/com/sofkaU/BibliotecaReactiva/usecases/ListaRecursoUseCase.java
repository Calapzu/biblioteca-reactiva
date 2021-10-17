package com.sofkaU.BibliotecaReactiva.usecases;

import com.sofkaU.BibliotecaReactiva.models.RecursoDto;
import com.sofkaU.BibliotecaReactiva.repositories.RepositorioRecurso;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class ListaRecursoUseCase implements Supplier<Flux<RecursoDto>> {
    private final RepositorioRecurso repositorioRecurso;
    private final MapperUtils mapperUtils;

    public ListaRecursoUseCase(RepositorioRecurso repositorioRecurso, MapperUtils mapperUtils) {
        this.repositorioRecurso = repositorioRecurso;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<RecursoDto> get() {
        return repositorioRecurso.findAll().map(mapperUtils.mapEntityToResource());
    }
}
