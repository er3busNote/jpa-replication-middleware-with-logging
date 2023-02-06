package com.opensw.master.domain.account.dao;

import com.opensw.master.domain.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findFirstByOrderByIdDesc();
    List<Account> findByIdGreaterThanEqual(Integer id);
    Long countBy();

    @Modifying
    @Query(value = "INSERT INTO account (id, username, email, phone, created_at, updated_at) VALUES (:id, :username, :email, :phone, :createdAt, :updatedAt)", nativeQuery = true)
    void insertAccount(@Param("id") int id, @Param("username") String username, @Param("email") String email, @Param("phone") String phone, @Param("createdAt") LocalDateTime createdAt, @Param("updatedAt") LocalDateTime updatedAt);
}
