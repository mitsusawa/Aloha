package com.product.aloha.repositories;

import com.product.aloha.Data.Data;
import com.product.aloha.Data.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
}

