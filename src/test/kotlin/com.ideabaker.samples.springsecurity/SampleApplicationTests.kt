package com.ideabaker.samples.springsecurity

import com.ideabaker.samples.springsecurity.domain.Movie
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 2018-12-29 16:18
 */
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SampleApplicationTests {

    @Autowired
    private val webTestClient: WebTestClient? = null

    @Test
    fun getAllMovies() {
        println("Test 1 executing getAllMovies")

        webTestClient!!
                .mutate()
                .filter(adminCredentials())
                .build()
                .get()
                .uri("/api/movie")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized
                .expectBodyList(Movie::class.java)
    }

    private fun userCredentials(): ExchangeFilterFunction {
        return ExchangeFilterFunctions.basicAuthentication("user", "password")
    }

    private fun adminCredentials(): ExchangeFilterFunction {
        return ExchangeFilterFunctions.basicAuthentication("admin", "password")
    }

}
