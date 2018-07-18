package com.product.aloha.repositories;

import com.product.aloha.Data.LessonArrayWrap;
import com.product.aloha.Data.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LessonArrayWrapRepository extends JpaRepository<LessonArrayWrap, Long> {
}

