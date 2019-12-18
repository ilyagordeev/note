package com.snote.note.domain


import org.hibernate.annotations.CreationTimestamp

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.NotNull

@Entity
class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    String heading
    @NotNull
    String note
    @CreationTimestamp
    Date timestamp
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ownerId")
    Users ownerId
}
