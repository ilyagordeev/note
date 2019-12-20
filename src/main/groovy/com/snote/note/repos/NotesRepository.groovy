package com.snote.note.repos

import com.snote.note.domain.Notes
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

import javax.transaction.Transactional

@Repository
interface NotesRepository extends PagingAndSortingRepository<Notes, Long> {

    @Query("SELECT e FROM Notes AS e WHERE lower(e.heading) LIKE lower(:search)")
    List<Notes> findNotesWithSearchQueryHeading(@Param("search") String searchQuery)

    @Query("SELECT e FROM Notes AS e WHERE lower(e.note) LIKE lower(:search)")
    List<Notes> findNotesWithSearchQueryNote(@Param("search") String searchQuery)

    @Transactional
    @Modifying
    @Query("delete from Notes where id= ?1")
    int deleteNoteById(Long id)

    @Query("FROM Notes ORDER BY timestamp desc")
    List<Notes> findAllByOrderByIdDesc()

}
