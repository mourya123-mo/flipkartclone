package com.jsp.flipkartclone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.flipkartclone.entity.Store;
import com.jsp.flipkartclone.requestdto.StoreRequest;
import com.jsp.flipkartclone.service.AuthService;
import com.jsp.flipkartclone.service.StoreService;
import com.jsp.flipkartclone.util.ResponseStructure;
import com.jsp.flipkartclone.util.SimpleResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/api/v1")
@RestController
@CrossOrigin(allowCredentials = "true", origins = "http://localhost:5173/")
public class StoreController {

	private StoreService storeService;

	@PostMapping("/stores")
	public ResponseEntity<ResponseStructure<Store>> createStore(@RequestBody StoreRequest storeRequest,
			@CookieValue(name = "at") String accessToken, @CookieValue(name = "rt") String refreshToken) {
		return storeService.createStore(storeRequest, accessToken, refreshToken);
	}

	@GetMapping("/stores/{storeId}")
	public ResponseEntity<SimpleResponseStructure> findStoreById(@PathVariable int storeId) {
		return storeService.findStoreById(storeId);
	}
	
	@GetMapping("/seller/{sellerId}/stores")
	public ResponseEntity<ResponseStructure<Store>>findStoreBySeller(@PathVariable int sellerId){
		
		return storeService.findStoreBySeller(sellerId);
	}
	
	@PostMapping("/stores/{storeId}")
	public ResponseEntity<ResponseStructure<Store>> updateStore(@RequestBody StoreRequest storeRequest,   @PathVariable int storeId){
		return storeService.updateStore(storeRequest, storeId);
	}
}
