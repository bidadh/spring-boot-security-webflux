package com.ideabaker.samples.springsecurity.domain

import org.springframework.data.annotation.*
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 2018-12-29 16:28
 */
abstract class AbstractDocument(
        @Id
        var id: Long? = null,

        @CreatedDate
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        var createdDate: Date? = null,

        @LastModifiedDate
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        var lastModifiedDate: Date? = null,

        @CreatedBy
        var createdBy: String? = null,

        @LastModifiedBy
        var lastModifiedBy: String? = null,

        @Version
        var version: Long? = null
)