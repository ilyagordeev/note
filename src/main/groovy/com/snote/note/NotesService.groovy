package com.snote.note

import com.snote.note.domain.Users
import com.snote.note.repos.NotesRepository
import com.snote.note.repos.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NotesService {

    @Autowired
    final NotesRepository notesRepository
    @Autowired
    final UsersRepository usersRepository

    Map<String, String> deleteNote(String id) {
        if (!id.isNumber() || id == null || id == "") return [result: 'error', value: 'id must present and be a number']
        def result = notesRepository.deleteNoteById(id as Long)
        [result: 'ok', value: result.toString()]
    }

    Map<String, String> addUser(String name, String password) {
        usersRepository.save(new Users(username: name, hashpass: password.sha256()))
        def value = "User $name added with hash ${password.sha256()}"
        [result: 'ok', value: value.toString()]
    }
}
