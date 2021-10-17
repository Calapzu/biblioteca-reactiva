package com.sofkaU.BibliotecaReactiva.usecases;

import com.sofkaU.BibliotecaReactiva.models.RecursoDto;
import com.sofkaU.BibliotecaReactiva.repositories.RepositorioRecurso;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class ActulizarRecursoUseCase implements GuardarRecurso{

    private final RepositorioRecurso repositorioRecurso;
    private final MapperUtils mapperUtils;

    public ActulizarRecursoUseCase(RepositorioRecurso repositorioRecurso, MapperUtils mapperUtils) {
        this.repositorioRecurso = repositorioRecurso;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<RecursoDto> apply(RecursoDto recursoDto) {
        Objects.requireNonNull(recursoDto.getId(), "El id del recurso es requerido");
        return repositorioRecurso
                .save(mapperUtils.mapperToResource().apply(recursoDto))
                .map(recurso -> mapperUtils.mapEntityToResource().apply(recurso));
    }
}
