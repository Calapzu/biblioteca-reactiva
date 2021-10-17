package com.sofkaU.BibliotecaReactiva.usecases;

import com.sofkaU.BibliotecaReactiva.models.RecursoDto;
import com.sofkaU.BibliotecaReactiva.repositories.RepositorioRecurso;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Service
public class RecomendarPorTipoYTematica implements RecomendarPortTipoYTematica {

    private final RepositorioRecurso repositorioRecurso;
    private final  MapperUtils mapperUtils;

    public RecomendarPorTipoYTematica(RepositorioRecurso repositorioRecurso, MapperUtils mapperUtils) {
        this.repositorioRecurso = repositorioRecurso;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<RecursoDto> get(String tipo, String tematica) {
        Objects.requireNonNull(tipo, "el tipo no puede esta vacio");
        Objects.requireNonNull(tematica, "la tematica no puede esta vacia");
        return repositorioRecurso.findAllByTipoYTematica(tipo, tematica).map(mapperUtils.mapEntityToResource()).distinct();
    }
}
