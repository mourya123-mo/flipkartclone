package com.jsp.flipkartclone.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jsp.flipkartclone.entity.Seller;
import com.jsp.flipkartclone.entity.Store;
import com.jsp.flipkartclone.exception.UserNotFoundById;
import com.jsp.flipkartclone.repo.SellerRepo;
import com.jsp.flipkartclone.repo.StoreRepo;
import com.jsp.flipkartclone.requestdto.StoreRequest;
import com.jsp.flipkartclone.service.StoreService;
import com.jsp.flipkartclone.util.ResponseStructure;
import com.jsp.flipkartclone.util.SimpleResponseStructure;

@Service
public class StoreServiceImpl implements StoreService {

	private StoreRepo storeRepo;

	private SimpleResponseStructure simpleStructure;

	private ResponseStructure<Store> responseStructure;

	private SellerRepo sellerRepo;

	public StoreServiceImpl(StoreRepo storeRepo, SimpleResponseStructure simpleStructure,
			ResponseStructure<Store> responseStructure, SellerRepo sellerRepo) {
		super();
		this.storeRepo = storeRepo;
		this.simpleStructure = simpleStructure;
		this.responseStructure = responseStructure;
		this.sellerRepo = sellerRepo;
	}

	public Store mapToStore(StoreRequest storeRequest) {

		return Store.builder().storeName(storeRequest.getStoreName()).about(storeRequest.getDescription()).build();

	}

	@Override
	public ResponseEntity<ResponseStructure<Store>> createStore(StoreRequest storeRequest, String accessToken,
			String refreshToken) {

		if (accessToken == null || refreshToken == null)
			throw new UsernameNotFoundException("seller not loggedin");

		else {

			Store store = mapToStore(storeRequest);
			storeRepo.save(store);
			responseStructure.setData(store);
			responseStructure.setMessage("store created");
			responseStructure.setStatus(HttpStatus.CREATED.value());

			return new ResponseEntity<ResponseStructure<Store>>(responseStructure, HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<SimpleResponseStructure> findStoreById(int storeId) {
		storeRepo.findById(storeId).orElseThrow(() -> new UserNotFoundById("store with given id is not present",
				HttpStatus.BAD_REQUEST.value(), "not logged in"));
		simpleStructure.setMessage("fetching store sucessful");
		simpleStructure.setStatus(HttpStatus.OK.value());
		return new ResponseEntity<>(simpleStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<Store>> findStoreBySeller(int sellerId) {
		Seller seller = sellerRepo.findById(sellerId).orElseThrow(() -> new UserNotFoundById("seller not present",
				HttpStatus.BAD_REQUEST.value(), "seller is not logged in"));

		Store store = seller.getStore();
		responseStructure.setData(store);
		responseStructure.setMessage("store based on seller");
		responseStructure.setStatus(HttpStatus.FOUND.value());

		return new ResponseEntity<ResponseStructure<Store>>(responseStructure, HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponseStructure<Store>> updateStore(StoreRequest storeRequest,int storeId) {
		Store store = storeRepo.findById(storeId).orElseThrow(
				() -> new UserNotFoundById("id not present in database", HttpStatus.NOT_FOUND.value(), "please login"));
			
		
			store.setStoreName(storeRequest.getStoreName());
			store.setAbout(	storeRequest.getDescription());
			storeRepo.save(store);
			responseStructure.setData(store);
			responseStructure.setMessage("updated Store sucessful");
			responseStructure.setStatus(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<Store>> (responseStructure,HttpStatus.OK) ;
	}
}
