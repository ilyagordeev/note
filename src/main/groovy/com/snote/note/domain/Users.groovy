package com.snote.note.domain

import com.snote.note.domain.Notes

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id
    private String username
    private String hashpass
 //   private boolean active

    @OneToMany(mappedBy = "ownerId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notes> noteId
}
