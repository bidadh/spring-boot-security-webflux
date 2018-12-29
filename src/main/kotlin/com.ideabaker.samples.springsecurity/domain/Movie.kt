package com.ideabaker.samples.springsecurity.domain

import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 2018-12-29 10:47
 */
@Document(collection = "movies")
class Movie(
        @NotBlank(message = "Movie title cannot be empty") val title: String,
        val genre: String
): AbstractDocument()
