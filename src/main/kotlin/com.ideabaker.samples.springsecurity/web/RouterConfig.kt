package com.ideabaker.samples.springsecurity.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 2018-12-29 11:24
 */
@Configuration
class RouterConfig @Autowired constructor(private val movieHandler: MovieHandler) {
    @Bean
    fun routes() = router {
        GET("/") {
            accept(MediaType.APPLICATION_JSON)
            movieHandler.listMovies(it)
        }
        GET("/api/movie") {
            accept(MediaType.APPLICATION_JSON)
            movieHandler.listMovies(it)
        }
        GET("/api/movie/{id}") {
            accept(MediaType.APPLICATION_JSON)
            movieHandler.getMovieById(it)
        }
        POST("/api/movie") {
            accept(MediaType.APPLICATION_JSON)
            movieHandler.saveMovie(it)
        }
        PUT("/api/movie/{id}") {
            accept(MediaType.APPLICATION_JSON)
            movieHandler.putMovie(it)
        }
        DELETE("/api/movie/{id}") {
            accept(MediaType.APPLICATION_JSON)
            movieHandler.deleteMovie(it)
        }
    }
}
