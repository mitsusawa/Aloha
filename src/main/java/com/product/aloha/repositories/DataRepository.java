package com.product.aloha.repositories;

import com.product.aloha.Data.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface DataRepository extends JpaRepository<Data, Long> {
	public Optional<Data> findById(Long id);
	public List<Data> findByUserName(String name);
	public List<Data> findByUserNameLike(String name);
	public <T> T findOneByUserName(String name, Class<T> type);
}

