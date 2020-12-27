package com.fortune.usercraft.repository;

import com.fortune.usercraft.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUid(String uid);
    Optional<User> findByPhone(String phone);
    boolean existsByUsername(String username);
    boolean existsByUid(String uid);
    boolean existsByPhone(String phone);
}
