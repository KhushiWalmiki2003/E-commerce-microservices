package com.ecommerce.user_service.Repo;

import com.ecommerce.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);


    Optional<User> findByEmail(String email);
}
