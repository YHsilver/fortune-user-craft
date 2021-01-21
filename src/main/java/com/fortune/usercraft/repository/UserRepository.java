package com.fortune.usercraft.repository;

import com.fortune.usercraft.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByPhone(String phone);
    boolean existsByUserId(String userId);
    boolean existsByPhone(String phone);
}
