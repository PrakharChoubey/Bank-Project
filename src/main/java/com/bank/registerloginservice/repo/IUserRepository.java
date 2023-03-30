package com.bank.registerloginservice.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.registerloginservice.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser>findByUsername(String username);

}
