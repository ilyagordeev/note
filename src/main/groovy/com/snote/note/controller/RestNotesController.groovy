package com.snote.note.controller

import com.snote.note.NotesService
import com.snote.note.repos.NotesRepository
import com.snote.note.repos.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
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

    @PostMapping
    Map<String, Object> getOneNote(HttpServletResponse response, HttpServletRequest request) {
        String type = request.getParameter "type"
        println(type)
        response.addHeader("Access-Control-Allow-Origin", "*")
        switch (type) {
            case 'adduser':
                return notesService.addUser(request.getParameter("name"), request.getParameter("password"))
                break
            case 'note':
                return notesService.getOneNote(request.getParameter("id"))
                break
            case 'delete':
                return notesService.deleteNote(request.getParameter("id"))
                break
            case 'contents':
                return notesService.contents()
                break
            case 'addnote':
                return notesService.addNote(
                        request.getParameter("heading"),
                        request.getParameter("note"),
                        usersRepository.findById(1L).get()
                )
                break
            case 'edit':
                return notesService.editNote(
                        request.getParameter("heading"),
                        request.getParameter("note"),
                        request.getParameter("id")
                )
                break
            case 'search':
                return notesService.searchByText(request.getParameter("text"))
                break
            case 'search_head':
                return notesService.searchByHeading(request.getParameter("text"))
                break
            default:
                return [result: 'error', value: 'type not valid']
        }
    }
}
