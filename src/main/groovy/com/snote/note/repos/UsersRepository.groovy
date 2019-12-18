package com.snote.note.repos

import com.snote.note.domain.Users
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository extends CrudRepository<Users, Long> {

}