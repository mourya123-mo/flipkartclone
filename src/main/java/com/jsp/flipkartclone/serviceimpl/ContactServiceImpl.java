package com.jsp.flipkartclone.serviceimpl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.flipkartclone.entity.Adress;
import com.jsp.flipkartclone.entity.Contact;
import com.jsp.flipkartclone.exception.ConstRaintViolationException;
import com.jsp.flipkartclone.exception.UserNotFoundById;
import com.jsp.flipkartclone.repo.AddressRepo;
import com.jsp.flipkartclone.repo.ContactRepo;
import com.jsp.flipkartclone.requestdto.ContactRequest;
import com.jsp.flipkartclone.responsedto.ContactResponse;
import com.jsp.flipkartclone.service.ContactService;
import com.jsp.flipkartclone.util.ResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {

	private ContactRepo contactRepo;
	private AddressRepo addressRepo;
	private ResponseStructure<Contact> responseStructure;
	private ResponseStructure<List<Contact>> contactListStructure;
	private ResponseStructure<ContactResponse> contactResponse;

	public Contact mapToContact(ContactRequest contactRequest, Adress address) {

		return Contact.builder().priority(contactRequest.getPriority()).name(contactRequest.getName())
				.contactNo(contactRequest.getContactNo()).adress(address).build();
	}

	private ContactResponse mapToContactResponse(Contact contact) {

		return ContactResponse.builder().contactId(contact.getContactId()).contactNo(contact.getContactNo())
				.name(contact.getName()).priority(contact.getPriority()).build();
	}

	@Override
	public ResponseEntity<ResponseStructure<Contact>> addContact(ContactRequest contactRequest, int addressId) {

		Adress address = addressRepo.findById(addressId)
				.orElseThrow(() -> new UserNotFoundById("Address with id " + addressId + " not found",
						HttpStatus.BAD_REQUEST.value(), "Address not found"));

		if (address.getContactList().size() == 2) {
			throw new ConstRaintViolationException("More than two contacts are not allowed for this address",
					HttpStatus.ALREADY_REPORTED.value(), "Constraint violation");
		} else {
			Contact contact = mapToContact(contactRequest, address);
			address.getContactList().add(contact);

			contactRepo.save(contact);
			addressRepo.save(address);

			responseStructure.setData(contact);
			responseStructure.setMessage("Contact saved successfully");
			responseStructure.setStatus(HttpStatus.OK.value());
		}
		return new ResponseEntity<ResponseStructure<Contact>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<Contact>> updateContact(ContactRequest contactRequest, int contactId) {
		Contact contact = contactRepo.findById(contactId)
				.orElseThrow(() -> new UserNotFoundById("id is not present in database", HttpStatus.NOT_FOUND.value(),
						"contact details not found"));

		contact.setName(contactRequest.getName());
		contact.setContactNo(contactRequest.getContactNo());
		contact.setPriority(contactRequest.getPriority());

		contactRepo.save(contact);
		responseStructure.setData(contact);
		responseStructure.setMessage("contact updated sucessfully");
		responseStructure.setStatus(HttpStatus.OK.value());

		return new ResponseEntity<ResponseStructure<Contact>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<ContactResponse>> findContactById(int contactId) {
		Contact contact = contactRepo.findById(contactId).orElseThrow(
				() -> new UserNotFoundById("id not found", HttpStatus.NOT_FOUND.value(), "not found in database"));
		contactResponse.setData(mapToContactResponse(contact));
		contactResponse.setMessage("fetched sucessfully");
		contactResponse.setStatus(HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseStructure<ContactResponse>>(contactResponse, HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Contact>>> findContactByAddress(int addressId) {
		Adress adress = addressRepo.findById(addressId).orElseThrow(() -> new UserNotFoundById("adress not present in database",
				HttpStatus.NOT_FOUND.value(), "id not found"));
		
		List<Contact> contactList = adress.getContactList();
		contactListStructure.setData(contactList);
		contactListStructure.setMessage("fetched sucessfully");
		contactListStructure.setStatus(HttpStatus.FOUND.value());
		
		return new ResponseEntity<ResponseStructure<List<Contact>>>(contactListStructure, HttpStatus.FOUND);
	}

}
