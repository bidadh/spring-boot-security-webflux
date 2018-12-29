package com.ideabaker.samples.springsecurity.domain

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.repository.NoRepositoryBean

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 2018-12-29 16:34
 */
@NoRepositoryBean
interface BaseRepository<T> : ReactiveMongoRepository<T, Long>