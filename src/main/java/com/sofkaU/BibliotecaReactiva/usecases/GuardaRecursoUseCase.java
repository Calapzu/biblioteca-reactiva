package com.sofkaU.BibliotecaReactiva.usecases;

import com.sofkaU.BibliotecaReactiva.collections.Recurso;
import com.sofkaU.BibliotecaReactiva.models.RecursoDto;
import com.sofkaU.BibliotecaReactiva.repositories.RepositorioRecurso;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class GuardaRecursoUseCase implements GuardarRecurso{

    private final RepositorioRecurso repositorioRecurso;
    private final MapperUtils mapperUtils;

    public GuardaRecursoUseCase(RepositorioRecurso repositorioRecurso, MapperUtils mapperUtils) {
        this.repositorioRecurso = repositorioRecurso;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<RecursoDto> apply(RecursoDto recursoDto) {
        return repositorioRecurso
                .save(mapperUtils.mapperToResource().apply(recursoDto))
                .map(recurso -> mapperUtils.mapEntityToResource().apply(recurso));
    }
}
