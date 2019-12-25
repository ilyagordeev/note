package com.snote.note.repos

import com.snote.note.domain.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username)
}