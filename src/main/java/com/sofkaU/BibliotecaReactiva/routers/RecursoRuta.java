package com.sofkaU.BibliotecaReactiva.routers;

import com.sofkaU.BibliotecaReactiva.models.RecursoDto;
import com.sofkaU.BibliotecaReactiva.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RecursoRuta {
    @Bean
    public RouterFunction<ServerResponse> traerTodos(ListaRecursoUseCase listaRecursoUseCase) {
        return route(GET("/recursos"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listaRecursoUseCase.get(), RecursoDto.class)
                        )
        );
    }

    @Bean
    public RouterFunction<ServerResponse> guardar(GuardaRecursoUseCase guardaRecursoUseCase) {
        Function<RecursoDto, Mono<ServerResponse>> executor = recursoDto -> guardaRecursoUseCase.apply(recursoDto)
                .flatMap(recursoDto1 -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(recursoDto1)
                );
        return route(POST("/recusos/agregar").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(RecursoDto.class).flatMap(executor)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> actualizar(ActulizarRecursoUseCase actulizarRecursoUseCase) {
        Function<RecursoDto, Mono<ServerResponse>> executor = recursoDto -> actulizarRecursoUseCase.apply(recursoDto)
                .flatMap(recursoDto1 -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(recursoDto1)
                );

        return route(PUT("/recursos/editar")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(RecursoDto.class)
                        .flatMap(executor)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> eliminar(EliminarRecursoUseCase eliminarRecursoUseCase){
        return route(DELETE("/recursos/eliminar/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(eliminarRecursoUseCase.apply(request.pathVariable("id")),
                                Void.class
                        ))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> disponibilidad(ComprobarDisponibilidadUseCase comprobarDisponibilidadUseCase){
        return route(GET("/recursos/disponibilidad/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(comprobarDisponibilidadUseCase.apply(request.pathVariable("id")), String.class))
                        .onErrorResume((Error) -> ServerResponse.badRequest().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> prestar(PrestarRecursoUseCase prestarRecursoUseCase){
        return route(PUT("/recursos/prestar/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(prestarRecursoUseCase.apply(request.pathVariable("id")), String.class))
                        .onErrorResume((error) -> ServerResponse.badRequest().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> recomendarPortipo(RecomendarPorTipoUseCase recomendarPorTipoUseCase){
        return route(GET("/recursos/recomendarportipo/{tipo}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(recomendarPorTipoUseCase.get(request.pathVariable("tipo")), RecursoDto.class))
                        .onErrorResume((error) -> ServerResponse.badRequest().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> recomendarPorTematica(RecomendarPortematicaUseCase recomendarPortematicaUseCase){
        return route(GET("/recursos/recomendarportipo/{tematica}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(recomendarPortematicaUseCase.get(request.pathVariable("tematica")), RecursoDto.class))
                        .onErrorResume((error) -> ServerResponse.badRequest().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> recomendarPorTematicaYTipo(RecomendarPorTipoYTematica recomendarPorTipoYTematica){
        return route(GET("/recursos/recomendarportipo/{tipo}/{tematica}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(recomendarPorTipoYTematica.get(request.pathVariable("tipo"), request.pathVariable("tematica")), RecursoDto.class))
                        .onErrorResume((error) -> ServerResponse.badRequest().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> regresarRecurso(RegresarRecursoUseCase regresarRecursoUseCase){
        return route(
                PUT("/recursos/regresar/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(regresarRecursoUseCase.apply(request.pathVariable("id")), String.class))
                        .onErrorResume((error) -> ServerResponse.badRequest().build())
        );
    }


}
