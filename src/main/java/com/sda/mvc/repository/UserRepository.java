package com.sda.mvc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.sda.mvc.model.dao.UserDao;

public interface UserRepository extends JpaRepository<UserDao, Long> {

    Optional<UserDao> getUserByLogin(@Param("login") String login);

}
