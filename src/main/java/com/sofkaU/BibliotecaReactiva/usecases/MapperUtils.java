package com.sofkaU.BibliotecaReactiva.usecases;

import com.sofkaU.BibliotecaReactiva.collections.Recurso;
import com.sofkaU.BibliotecaReactiva.models.RecursoDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperUtils {
    public Function<RecursoDto, Recurso> mapperToResource(){
        return actualizarRecurso ->{
            var recurso = new Recurso();
            recurso.setId(actualizarRecurso.getId());
            recurso.setNombreRecurso(actualizarRecurso.getNombreRecurso());
            recurso.setTematica(actualizarRecurso.getTematica());
            recurso.setTipo(actualizarRecurso.getTipo());
            recurso.setFechaPrestamo(actualizarRecurso.getFechaPrestamo());
            recurso.setCantidadDisponible(actualizarRecurso.getCantidadDisponible());
            recurso.setCantidadPrestada(actualizarRecurso.getCantidadPrestada());
            return recurso;
        };
    }

    public Function<Recurso, RecursoDto> mapEntityToResource(){
        return entity -> new RecursoDto(
                entity.getId(),
                entity.getNombreRecurso(),
                entity.getTematica(),
                entity.getTipo(),
                entity.getFechaPrestamo(),
                entity.getCantidadDisponible(),
                entity.getCantidadPrestada()
        );
    }
}
