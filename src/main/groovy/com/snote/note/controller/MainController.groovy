package com.snote.note.controller

import com.snote.note.domain.Notes
import com.snote.note.repos.NotesRepository
import com.snote.note.repos.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class MainController {

    @Autowired
    private final NotesRepository notesRepository
    @Autowired
    final UsersRepository usersRepository


    @GetMapping(value = "/old")
    String handleRequest(HttpServletResponse response, Model model) {
        notesRepository.save(new Notes(
                heading: "Тут что-то будет",
                note: "Хибирнейт ит!",
                ownerId: usersRepository.findById(4L).get()))

        response.addHeader("Access-Control-Allow-Origin", "*")

        model.addAttribute("notes", notesRepository.findAll())

        $/index/$
    }

}
