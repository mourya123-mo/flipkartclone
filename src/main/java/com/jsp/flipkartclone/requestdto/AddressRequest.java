package com.jsp.flipkartclone.requestdto;

import com.jsp.flipkartclone.enums.AdressType;

import lombok.Data;

@Data
public class AddressRequest {

	private String streetAdress;
	private String streetAdressAdditional;
	private String city;
	private String state;
	private String country;
	private int pincode;
	private AdressType adressType;
}
