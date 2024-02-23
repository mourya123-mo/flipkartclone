package com.jsp.flipkartclone.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.flipkartclone.entity.Contact;
import com.jsp.flipkartclone.requestdto.ContactRequest;
import com.jsp.flipkartclone.responsedto.ContactResponse;
import com.jsp.flipkartclone.service.ContactService;
import com.jsp.flipkartclone.util.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ContactController {
	
	private ContactService contactService;

	@PostMapping("/contacts/{addressId}")
	public ResponseEntity<ResponseStructure<Contact>>addContact(@RequestBody ContactRequest contactRequest , @PathVariable int addressId){
		return contactService.addContact( contactRequest , addressId) ;
		
	}
	@PostMapping("/contact/{contactId}")
	public ResponseEntity<ResponseStructure<Contact>> updateContact(@RequestBody ContactRequest contactRequest, @PathVariable int contactId){
		return contactService.updateContact( contactRequest, contactId);
		
	}
	@GetMapping("/contacts/{contactId}")
	public ResponseEntity<ResponseStructure<ContactResponse>> findContactById(@PathVariable int contactId){
		return contactService. findContactById( contactId);
		
	}
	@GetMapping("/adress/{addressId}")
	public ResponseEntity<ResponseStructure<List<Contact>>> findContactByAddress(@PathVariable int addressId){
		return contactService.findContactByAddress( addressId);
		
	}
	
}
