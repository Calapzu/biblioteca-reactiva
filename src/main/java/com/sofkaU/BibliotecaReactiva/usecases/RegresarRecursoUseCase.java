package com.sofkaU.BibliotecaReactiva.usecases;

import com.sofkaU.BibliotecaReactiva.repositories.RepositorioRecurso;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class RegresarRecursoUseCase implements Function<String, Mono<String>> {

    private final RepositorioRecurso repositorioRecurso;
    private final ActulizarRecursoUseCase actulizarRecursoUseCase;
    private final MapperUtils mapperUtils;

    public RegresarRecursoUseCase(RepositorioRecurso repositorioRecurso, MapperUtils mapperUtils) {
        this.repositorioRecurso = repositorioRecurso;
        this.actulizarRecursoUseCase = new ActulizarRecursoUseCase(repositorioRecurso, mapperUtils);
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<String> apply(String id) {
        Objects.requireNonNull(id,"El id no puede ser nulo");
        return repositorioRecurso.findById(id).flatMap(
                recurso -> {
                    if (recurso.getCantidadPrestada() > 0){
                        recurso.setCantidadPrestada(recurso.getCantidadPrestada() - 1);
                        return actulizarRecursoUseCase.apply(mapperUtils.mapEntityToResource().apply(recurso))
                                .thenReturn("El recurso " + recurso.getNombreRecurso() + " se ha devuelto");
                    }
                    return Mono.just("El recurso " + recurso.getNombreRecurso() + " no se puede devolver, porque no se ha prestado");
                }
        );

    }
}
