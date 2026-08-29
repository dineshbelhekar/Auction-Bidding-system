package com.dinesh.Auth_Service.repository;

import com.dinesh.Auth_Service.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends JpaRepository<User, String> {
}
