package com.ideabaker.samples.springsecurity.repository;

import com.ideabaker.samples.springsecurity.model.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movie, Long> {
}
