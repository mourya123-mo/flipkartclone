package com.jsp.flipkartclone.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flipkartclone.entity.Store;


public interface StoreRepo extends JpaRepository<Store, Integer> {
	
	Optional<Store> findByStoreName(String storeName);

}
