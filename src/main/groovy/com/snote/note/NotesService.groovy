package com.snote.note

import com.snote.note.domain.Notes
import com.snote.note.domain.Users
import com.snote.note.repos.NotesRepository
import com.snote.note.repos.UsersRepository
import groovy.text.SimpleTemplateEngine
import groovy.text.StreamingTemplateEngine
import groovy.text.XmlTemplateEngine
import groovy.text.markup.MarkupTemplateEngine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.ui.Model

@Service
class NotesService {

    @Autowired
    final NotesRepository notesRepository
    @Autowired
    final UsersRepository usersRepository

    // Удаление заметки
    Map<String, String> deleteNote(String idNote) {
        if (!idNote) return [result: 'error', value: 'id parameter not present']
        if (!idNote.isNumber()) return [result: 'error', value: 'id must be a number']
        if (!idNote.isLong()) return [result: 'error', value: 'id must be a integer number']
        def result = notesRepository.deleteNoteById(idNote as Long)
        //[result: 'ok', value: result.toString()]
        contents()
    }

    // Добавление пользователя
    Map<String, String> addUser(String name, String password) {
        usersRepository.save(new Users(username: name, hashpass: password.sha256()))
        def value = "User $name added with hash ${password.sha256()}"
        [result: 'ok', value: value.toString()]
    }

    // Получить одну заметку по id
    Map<String, Object> getOneNote(String idNote) {
        if (!idNote) return [result: 'error', value: 'id parameter not present']
        if (!idNote.isNumber()) return [result: 'error', value: 'id must be a number']
        if (!idNote.isLong()) return [result: 'error', value: 'id must be a integer number']
        Notes note
        notesRepository.findById(idNote as Long).ifPresent({n -> note = n})
        if (note == null) return [result: 'error', value: 'No such note']

        [result: 'ok', heading: note.heading, text: note.note, id: note.id, timestamp: note.timestamp]
    }

    // Добавить заметку
    Map<String, String> addNote(String heading, String text, Users user) {
        def noteNode = new Notes(
                heading: heading,
                note: text,
                ownerId: user
        )
        notesRepository.save(noteNode)
        contents()
    }

    // Изменить заметку
    Map<String, String> editNote(String heading, String text, Users user, String idNote) {
        if (!idNote) return [result: 'error', value: '"note" parameter not present']

        Notes note
        notesRepository.findById(idNote as Long).ifPresent({n -> note = n})
        if (note == null) return [result: 'error', value: 'No such note']

        note.heading = heading
        note.note = text

        notesRepository.save(note)

        contents()
    }

    // Поиск по заголовку
    Map<String, String> searchByHeading(String searched) {
        def notes = notesRepository.findNotesWithSearchQueryHeading("%${searched}%")
        if (!notes) return [result: 'nothing', value: 'Ничего не найдено']
        contents(notes)
    }

    // Поиск по тексту
    Map<String, String> searchByText(String searched) {
        def notes = notesRepository.findNotesWithSearchQueryNote("%${searched}%")
        if (!notes) return [result: 'nothing', value: 'Ничего не найдено']
        contents(notes)
    }

    // Список заголовков
    Map<String, String> contents(List<Notes> notes) {
        def month = [0: 'Января', 1: 'Февраля', 2: 'Марта', 3: 'Апреля',
                     4: 'Мая', 5: 'Июня', 6: 'Июля', 7: 'Августа',
                     8: 'Сентября', 9: 'Октября', 10: 'Ноября', 11: 'Декабря']

        def model = ["month": month, "notes": notes]
        def template = new MarkupTemplateEngine().createTemplateByPath('templates/block.tpl')

        String response = template.make(model)

        [result: 'ok', value: response]
    }

    // Список заголовков полностью
    Map<String, String> contents() {
        contents(notesRepository.findAll() as List<Notes>)
    }
}
