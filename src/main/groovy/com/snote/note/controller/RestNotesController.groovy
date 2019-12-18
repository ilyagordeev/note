package com.snote.note.controller

import com.snote.note.NotesService
import com.snote.note.domain.Notes
import com.snote.note.domain.Users
import com.snote.note.repos.NotesRepository
import com.snote.note.repos.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
class RestNotesController {

    @Autowired
    final NotesRepository notesRepository
    @Autowired
    final UsersRepository usersRepository
    @Autowired
    final NotesService notesService

    @GetMapping
    Map<String, String> getOneNote(Model model, HttpServletResponse response, HttpServletRequest request) {

       // def idNote = request.getParameter "note"
        def idNote = request.getHeader('note')
        if (idNote == "" || idNote == null) return [result: 'error', value: '"note" parameter not present']

        Notes note
        notesRepository.findById(idNote as Long).ifPresent({n -> note = n})
        if (note == null) return [result: 'error', value: 'No such note']

        def htmlTmpl = '<div class=\'container\'>\n' +
                '            <p class="heading">${heading}</p>\n' +
                '            <p class="note">${note}</p>\n' +
                '       </div>'

        def binding = ["heading":note.heading, "note":note.note]

        def engine = new groovy.text.SimpleTemplateEngine()
        def template = engine.createTemplate(htmlTmpl).make(binding)

        response.addHeader("Access-Control-Allow-Origin", "*")
        //notesRepository.findNotesWithSearchQueryHeading("%пожаловать%")
        //notesRepository.findNotesWithSearchQueryNote("%lissi%")

        [result: 'ok', value: template.toString()]
    }

    @DeleteMapping
    Map<String, String> deleteNote(Model model, HttpServletResponse response, HttpServletRequest request) {
        response.addHeader("Access-Control-Allow-Origin", "*")
        notesService.deleteNote(request.getParameter("id"))
    }

    @PostMapping("/adduser")
    Map<String, String> addUser(Model model, HttpServletResponse response, HttpServletRequest request) {
        response.addHeader("Access-Control-Allow-Origin", "*")
        notesService.addUser(request.getParameter("name"), request.getParameter("password"))
    }

}
