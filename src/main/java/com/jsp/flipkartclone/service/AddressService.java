package com.jsp.flipkartclone.service;

import org.springframework.http.ResponseEntity;

import com.jsp.flipkartclone.requestdto.AddressRequest;
import com.jsp.flipkartclone.responsedto.AddressResponse;
import com.jsp.flipkartclone.util.ResponseStructure;

public interface AddressService {

	ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest, int storeId);

	ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(AddressRequest addressRequest, int addressId);

	ResponseEntity<ResponseStructure<AddressResponse>> findAddressById(int addressId);

	ResponseEntity<ResponseStructure<AddressResponse>> findAdressByStore(int storeId);

}
