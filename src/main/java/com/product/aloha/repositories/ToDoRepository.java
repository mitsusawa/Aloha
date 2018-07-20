package com.product.aloha.repositories;

import com.product.aloha.Data.Lesson;
import com.product.aloha.Data.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}

