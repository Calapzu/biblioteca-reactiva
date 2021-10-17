package com.sofkaU.BibliotecaReactiva.usecases;

import com.sofkaU.BibliotecaReactiva.repositories.RepositorioRecurso;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class ComprobarDisponibilidadUseCase implements Function<String, Mono<String>> {

    private final RepositorioRecurso repositorioRecurso;

    public ComprobarDisponibilidadUseCase(RepositorioRecurso repositorioRecurso) {
        this.repositorioRecurso = repositorioRecurso;
    }

    @Override
    public Mono<String> apply(String id) {
        Objects.requireNonNull(id, "El id no puede ser nulo");
        return repositorioRecurso.findById(id)
                .map(recurso ->
                        recurso.getCantidadDisponible() > recurso.getCantidadPrestada()
                                ? String.valueOf("El recurso " + recurso.getNombreRecurso() + " disponible y cuenta con "
                                + (recurso.getCantidadDisponible() - recurso.getCantidadPrestada()) + " unidad(es) disponible(es)")
                                : String.valueOf("El recurso " + recurso.getNombreRecurso() + " no esta disponible " + " fue prestado "
                                + recurso.getFechaPrestamo())
                );
    }
}
