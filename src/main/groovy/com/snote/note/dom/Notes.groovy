package com.snote.note.dom


import org.hibernate.annotations.CreationTimestamp

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.NotNull

@Entity
class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    String heading
    @NotNull
    @Column(length = 4000)
    String note
    @CreationTimestamp
    Date timestamp

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    Users owner
}
