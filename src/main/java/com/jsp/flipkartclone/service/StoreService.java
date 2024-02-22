package com.jsp.flipkartclone.service;

import org.springframework.http.ResponseEntity;

import com.jsp.flipkartclone.entity.Store;
import com.jsp.flipkartclone.requestdto.StoreRequest;
import com.jsp.flipkartclone.util.ResponseStructure;
import com.jsp.flipkartclone.util.SimpleResponseStructure;

public interface StoreService {

	ResponseEntity<ResponseStructure<Store>> createStore(StoreRequest storeRequest,
			String accessToken, String refreshToken);

	ResponseEntity<SimpleResponseStructure> findStoreById(int storeId);

	ResponseEntity<ResponseStructure<Store>> findStoreBySeller(int sellerId);

	ResponseEntity<ResponseStructure<Store>> updateStore(StoreRequest storeRequest,int storeId);

	

}
