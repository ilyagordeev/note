package com.snote.note.controller

import com.snote.note.NotesService
import com.snote.note.dom.Role
import com.snote.note.dom.Users
import com.snote.note.repos.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class MainController {

    @Autowired
    final NotesService notesService
    @Autowired
    final UsersRepository usersRepository
    @Autowired
    final PasswordEncoder passwordEncoder


    @GetMapping("/")
    String handleRequest(@AuthenticationPrincipal Users user, Model model) {
        model.addAttribute("user", user.username)
        $/index/$
    }

    @GetMapping("/registration")
    String registration() {
        return "registration"
    }

    @PostMapping("/registration")
    String addUser(Users user, Model model) {
        Users userDB = usersRepository.findByUsername(user.username)
        if (userDB) {
            model.addAttribute("message", "User exist!")
            return "registration"
        }
        user.active = true
        user.roles = Collections.singleton(Role.USER)
        usersRepository.save(user)

        return "redirect:/login"
    }

}
