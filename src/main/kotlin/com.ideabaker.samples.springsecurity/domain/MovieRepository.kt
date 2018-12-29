package com.ideabaker.samples.springsecurity.domain

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 2018-12-29 10:50
 */
@Repository
interface MovieRepository : ReactiveMongoRepository<Movie, Long>
