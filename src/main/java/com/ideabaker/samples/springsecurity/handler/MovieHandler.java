package com.ideabaker.samples.springsecurity.handler;

import com.ideabaker.samples.springsecurity.model.Movie;
import com.ideabaker.samples.springsecurity.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class MovieHandler {

  private final MovieRepository movieRepository;

  @Autowired
  public MovieHandler(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  /**
   * GET ALL Movies
   */
  @PreAuthorize("hasRole('ADMIN')")
  public Mono<ServerResponse> listMovies(@SuppressWarnings("unused") ServerRequest request) {
    // fetch all Movies from repository
    Flux<Movie> movies = movieRepository.findAll();

    // build response
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(movies, Movie.class);
  }

  /**
   * GET a Movie by ID
   */
  public Mono<ServerResponse> getMovieById(ServerRequest request) {
    // parse path-variable
    long movieId = Long.valueOf(request.pathVariable("id"));

    // build notFound response
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    // get Movie from repository
    Mono<Movie> movieMono = movieRepository.findById(movieId);

    // build response
    return movieMono
      .flatMap(Movie -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(Movie)))
      .switchIfEmpty(notFound);
  }

  /**
   * POST a Movie
   */
  @PreAuthorize("hasAuthority('SCOPE_movie')")
  public Mono<ServerResponse> saveMovie(ServerRequest request) {
    Mono<Movie> movieMono = request.bodyToMono(Movie.class);

    return movieMono.flatMap(movieRepository::save)
    .flatMap(movie -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(movie)));
  }

  /**
   *	PUT a Movie
   */
  public Mono<ServerResponse> putMovie(ServerRequest request) {
    Mono<Movie> movieMono = request.bodyToMono(Movie.class);

    return movieMono.flatMap(movieRepository::save)
            .flatMap(movie -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(movie)));
  }

  /**
   *	DELETE a Movie
   */
  @PreAuthorize("hasRole('ADMIN')")
  public Mono<ServerResponse> deleteMovie(ServerRequest request) {
    // parse id from path-variable
    long movieId = Long.valueOf(request.pathVariable("id"));

    // get Movie from repository
    return movieRepository.deleteById(movieId)
            .flatMap(aVoid -> Mono.just("Movie Deleted Successfully"))
            .flatMap(strMono -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(fromObject(strMono)));
  }
}
