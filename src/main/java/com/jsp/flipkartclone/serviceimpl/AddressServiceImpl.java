package com.jsp.flipkartclone.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.flipkartclone.entity.Adress;
import com.jsp.flipkartclone.entity.Store;
import com.jsp.flipkartclone.exception.UserNotFoundById;
import com.jsp.flipkartclone.repo.AddressRepo;
import com.jsp.flipkartclone.repo.StoreRepo;
import com.jsp.flipkartclone.requestdto.AddressRequest;
import com.jsp.flipkartclone.responsedto.AddressResponse;
import com.jsp.flipkartclone.service.AddressService;
import com.jsp.flipkartclone.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

	private AddressRepo addressRepo;

	private StoreRepo storeRepo;

	private ResponseStructure<AddressResponse> responseStructure;

	public AddressResponse mapToAdressResponse(Adress adress) {

		return AddressResponse.builder().adressId(adress.getAdressId()).adressType(adress.getAdressType())
				.city(adress.getCity()).pincode(adress.getPincode()).country(adress.getCountry())
				.state(adress.getState()).streetAdress(adress.getStreetAdress())
				.streetAdressAdditional(adress.getStreetAdressAdditional()).build();
	}

	public Adress mapToAdress(AddressRequest addressRequest) {
		return Adress.builder().adressType(addressRequest.getAdressType()).state(addressRequest.getState())
				.city(addressRequest.getCity()).country(addressRequest.getCountry())
				.streetAdress(addressRequest.getStreetAdress())
				.streetAdressAdditional(addressRequest.getStreetAdressAdditional()).pincode(addressRequest.getPincode())
				.build();
	}

	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest, int storeId) {
		Store store = storeRepo.findById(storeId)
				.orElseThrow(() -> new UserNotFoundById("store not present of given id", HttpStatus.NOT_FOUND.value(),
						"store not found"));

		Adress adress = mapToAdress(addressRequest);
		addressRepo.save(adress);
		store.setAdress(adress);
		storeRepo.save(store);

		responseStructure.setData(mapToAdressResponse(adress));
		responseStructure.setMessage("adress saved sucessfully");
		responseStructure.setStatus(HttpStatus.OK.value());

		return new ResponseEntity<ResponseStructure<AddressResponse>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(AddressRequest addressRequest,
			int addressId) {
		Adress adress = addressRepo.findById(addressId)
				.orElseThrow(() -> new UserNotFoundById("adress with given id is not present",
						HttpStatus.BAD_REQUEST.value(), "address not present "));

		adress.setAdressType(addressRequest.getAdressType());
		adress.setCity(addressRequest.getCity());
		adress.setCountry(addressRequest.getCountry());
		adress.setPincode(addressRequest.getPincode());
		adress.setState(addressRequest.getState());
		adress.setStreetAdress(addressRequest.getStreetAdress());
		adress.setStreetAdressAdditional(addressRequest.getStreetAdressAdditional());

		addressRepo.save(adress);

		responseStructure.setData(mapToAdressResponse(adress));
		responseStructure.setMessage("adress updated sucessfully");
		responseStructure.setStatus(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<AddressResponse>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> findAddressById(int addressId) {
		Adress adress = addressRepo.findById(addressId)
				.orElseThrow(() -> new UserNotFoundById("adress not found with given id",
						HttpStatus.BAD_REQUEST.OK.value(), "id not found"));
		responseStructure.setData(mapToAdressResponse(adress));
		responseStructure.setMessage("sucessful");
		responseStructure.setStatus(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<AddressResponse>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> findAdressByStore(int storeId) {
		Store store = storeRepo.findById(storeId).orElseThrow(() -> new UserNotFoundById("store with given id is not present",
				HttpStatus.BAD_REQUEST.value(), "store not found"));
		Adress adress = store.getAdress();
		responseStructure.setData(mapToAdressResponse(adress));
		responseStructure.setMessage("sucessful");
		responseStructure.setStatus(HttpStatus.OK.value());
		return new   ResponseEntity<ResponseStructure<AddressResponse>>(responseStructure, HttpStatus.OK);
	}

}
