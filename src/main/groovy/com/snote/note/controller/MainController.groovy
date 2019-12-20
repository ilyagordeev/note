package com.snote.note.controller


import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {


    @GetMapping(value = "/")
    String handleRequest() {

        $/index/$
    }

}
