package com.snote.note.controller

import com.snote.note.NotesService
import com.snote.note.repos.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {

    @Autowired
    final NotesService notesService
    @Autowired
    final UsersRepository usersRepository


    @GetMapping(value = "/")
    String handleRequest() {

        if (!usersRepository.findById(1L).present)
            notesService.addUser('Admin', 'hardpass')

        $/index/$
    }

}
