package com.jsp.flipkartclone.entity;

import com.jsp.flipkartclone.enums.AdressType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Adress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int adressId;
	private String streetAdress;
	private String streetAdressAdditional;
	private String city;
	private String state;
	private String country;
	private int pincode;
	private AdressType adressType;

	@OneToOne
	private Store store;
}
