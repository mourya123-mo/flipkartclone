package com.jsp.flipkartclone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.flipkartclone.requestdto.AddressRequest;
import com.jsp.flipkartclone.responsedto.AddressResponse;
import com.jsp.flipkartclone.service.AddressService;
import com.jsp.flipkartclone.util.ResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AddressController {

	private AddressService addressService;

	@PostMapping("/stores/{storeId}/address")
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(@RequestBody AddressRequest addressRequest,
			@PathVariable int storeId) {
		return addressService.addAddress(addressRequest, storeId);

	}

	@PostMapping("/address/{addressId}")
	public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(@RequestBody AddressRequest addressRequest,
			@PathVariable int addressId) {
		return addressService.updateAddress(addressRequest, addressId);

	}
	@GetMapping("/address/{addressId}")
	public ResponseEntity<ResponseStructure<AddressResponse>>findAddressById(@PathVariable int addressId){
		return addressService.findAddressById( addressId);
		
	}
	@GetMapping("/stores/adress/{storeId}")
	public ResponseEntity<ResponseStructure<AddressResponse>> findAdressByStore(@PathVariable int storeId){
		return addressService. findAdressByStore( storeId) ;
		
	}
	
}
