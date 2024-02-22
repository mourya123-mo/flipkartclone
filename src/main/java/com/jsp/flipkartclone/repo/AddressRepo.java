package com.jsp.flipkartclone.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flipkartclone.entity.Adress;

public interface AddressRepo extends JpaRepository<Adress, Integer> {

}
