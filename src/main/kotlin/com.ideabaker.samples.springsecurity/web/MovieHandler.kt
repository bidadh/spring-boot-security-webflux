package com.ideabaker.samples.springsecurity.web

import com.ideabaker.samples.springsecurity.domain.MovieRepository
import com.ideabaker.samples.springsecurity.domain.Movie
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 2018-12-29 10:51
 */
@Component
class MovieHandler @Autowired constructor(private val movieRepository: MovieRepository) {

    /**
     * GET ALL Movies
     */
    @PreAuthorize("hasRole('ADMIN')")
    fun listMovies(request: ServerRequest): Mono<ServerResponse> {
        // fetch all Movies from repository
        val movies = movieRepository.findAll()

        // build response
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(movies, Movie::class.java)
    }

    /**
     * GET a Movie by ID
     */
    fun getMovieById(request: ServerRequest): Mono<ServerResponse> {
        // parse path-variable
        val movieId = java.lang.Long.valueOf(request.pathVariable("id"))

        // build notFound response
        val notFound = ServerResponse.notFound().build()

        // get Movie from repository
        val movieMono = movieRepository.findById(movieId)

        // build response
        return movieMono
                .flatMap({ Movie -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject<Any>(Movie)) })
                .switchIfEmpty(notFound)
    }

    /**
     * POST a Movie
     */
    @PreAuthorize("hasAuthority('SCOPE_movie')")
    fun saveMovie(request: ServerRequest): Mono<ServerResponse> {
        val movieMono = request.bodyToMono(Movie::class.java)

        return movieMono
                .flatMap { movie -> movieRepository.save(movie) }
                .flatMap { movie -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject<Any>(movie))
                }
    }

    /**
     * PUT a Movie
     */
    fun putMovie(request: ServerRequest): Mono<ServerResponse> {
        val movieMono = request.bodyToMono(Movie::class.java)

        return movieMono
                .flatMap { movie -> movieRepository.save(movie) }
                .flatMap { movie ->
                    ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fromObject<Any>(movie))
                }
    }

    /**
     * DELETE a Movie
     */
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteMovie(request: ServerRequest): Mono<ServerResponse> {
        // parse id from path-variable
        val movieId = java.lang.Long.valueOf(request.pathVariable("id"))

        // get Movie from repository
        return movieRepository.deleteById(movieId)
                .flatMap { Mono.just("Movie Deleted Successfully") }
                .flatMap { msg -> ServerResponse
                        .ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(fromObject(msg)) }
    }
}
