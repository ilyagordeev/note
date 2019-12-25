package com.snote.note.repos;

import com.snote.note.dom.Notes;
import com.snote.note.dom.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface NotesRepository extends PagingAndSortingRepository<Notes, Long> {

    @Query("SELECT e FROM Notes AS e WHERE e.owner= ?1 and lower(e.heading) LIKE lower(?2)")
    List<Notes> findNotesWithSearchQueryHeading(Users id, String searchQuery);

    @Query("SELECT e FROM Notes AS e WHERE e.owner= ?1 and lower(e.note) LIKE lower(?2)")
    List<Notes> findNotesWithSearchQueryNote(Users id, String searchQuery);

    @Transactional
    @Modifying
    @Query("delete from Notes where id= ?1")
    int deleteNoteById(Long id);

    @Query("FROM Notes ORDER BY timestamp desc")
    List<Notes> findAllByOrderByIdDesc();

    List<Notes> findAllByOwnerOrderByTimestampDesc(Users owner);

}
