package com.jsp.flipkartclone.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flipkartclone.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{

}
