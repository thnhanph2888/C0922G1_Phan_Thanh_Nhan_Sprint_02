package com.example.system_management_restaurant_qtgm.repository;

import com.example.system_management_restaurant_qtgm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAccountRepository extends JpaRepository<Account, Integer> {
     @Query(nativeQuery = true, value = "select acc.* from account as acc where acc.username = :username")
     Account findAccountByUsername(@Param("username") String username);
}
