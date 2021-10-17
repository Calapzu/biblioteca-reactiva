package com.sofkaU.BibliotecaReactiva.usecases;

import com.sofkaU.BibliotecaReactiva.models.RecursoDto;
import com.sofkaU.BibliotecaReactiva.repositories.RepositorioRecurso;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Service
@Validated
public class RecomendarPorTipoUseCase implements RecomendarPorTipo {

    private final RepositorioRecurso repositorioRecurso;
    private final  MapperUtils mapperUtils;

    public RecomnendarPorTipoUseCase(RepositorioRecurso repositorioRecurso, MapperUtils mapperUtils) {
        this.repositorioRecurso = repositorioRecurso;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<RecursoDto> get(String type) {
        return repositorioRecurso.findAllByTipo(type).map(mapperUtils.mapEntityToResource());
    }
}
