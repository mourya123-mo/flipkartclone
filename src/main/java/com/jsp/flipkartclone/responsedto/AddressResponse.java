package com.jsp.flipkartclone.responsedto;

import com.jsp.flipkartclone.enums.AdressType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {

	private int adressId;
	private String streetAdress;
	private String streetAdressAdditional;
	private String city;
	private String state;
	private String country;
	private int pincode;
	private AdressType adressType;
}
