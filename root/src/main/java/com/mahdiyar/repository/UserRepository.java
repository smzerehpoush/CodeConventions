package com.mahdiyar.repository;

import com.mahdiyar.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
