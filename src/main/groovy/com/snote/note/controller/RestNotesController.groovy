package com.snote.note.controller

import com.snote.note.NotesService
import com.snote.note.dom.Users
import com.snote.note.repos.NotesRepository
import com.snote.note.repos.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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
    Map<String, Object> getOneNote(
            @AuthenticationPrincipal Users user,
            @RequestParam String type,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        println(type)
        response.addHeader("Access-Control-Allow-Origin", "*")
        switch (type) {
            case 'addnote':
            return notesService.addNote(
                    request.getParameter("heading"),
                    request.getParameter("note"),
                    user
            )
            break
//            case 'adduser':
//                return notesService.addUser(request.getParameter("name"), request.getParameter("password"))
//                break
            case 'note':
                return notesService.getOneNote(request.getParameter("id"), user)
                break
            case 'delete':
                return notesService.deleteNote(request.getParameter("id"), user)
                break
            case 'contents':
                return notesService.contentsByUser(user)
                break

            case 'edit':
                return notesService.editNote(
                        request.getParameter("heading"),
                        request.getParameter("note"),
                        request.getParameter("id"),
                        user
                )
                break
            case 'search':
                return notesService.searchByText(request.getParameter("text"), user)
                break
            case 'search_head':
                return notesService.searchByHeading(request.getParameter("text"), user)
                break
            default:
                return [result: 'error', value: 'type not valid']
        }
    }
}
