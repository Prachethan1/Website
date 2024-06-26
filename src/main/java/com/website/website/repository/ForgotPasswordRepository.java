package com.website.website.repository;

import com.website.website.entity.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, Long>{

    ForgotPasswordToken findByToken(String token);

}