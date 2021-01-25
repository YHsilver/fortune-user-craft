package com.fortune.usercraft.repository;

import com.fortune.usercraft.entity.UserCore;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCoreRepo extends PagingAndSortingRepository<UserCore, Integer> {
    Optional<UserCore> findByUserId(String userId);
    Optional<UserCore> findByUsername(String username);
    boolean existsByUserId(String userId);
    boolean existsByUsername(String username);
}
