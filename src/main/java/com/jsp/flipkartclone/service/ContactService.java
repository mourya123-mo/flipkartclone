package com.jsp.flipkartclone.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.jsp.flipkartclone.entity.Contact;
import com.jsp.flipkartclone.requestdto.ContactRequest;
import com.jsp.flipkartclone.responsedto.ContactResponse;
import com.jsp.flipkartclone.util.ResponseStructure;

public interface ContactService {

	ResponseEntity<ResponseStructure<Contact>> addContact(ContactRequest contactRequest, int addressId);

	ResponseEntity<ResponseStructure<Contact>> updateContact(ContactRequest contactRequest, int contactId);

	ResponseEntity<ResponseStructure<ContactResponse>> findContactById(int contactId);

	ResponseEntity<ResponseStructure<List<Contact>>> findContactByAddress(int addressId);

}
