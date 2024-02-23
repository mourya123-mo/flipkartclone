package com.jsp.flipkartclone.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flipkartclone.entity.Contact;

public interface ContactRepo extends JpaRepository<Contact, Integer> {

}
