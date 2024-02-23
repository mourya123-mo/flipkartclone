package com.jsp.flipkartclone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsp.flipkartclone.enums.Priority;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int contactId;
	private String name;
	private long contactNo;
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	@JsonIgnore
	@ManyToOne
	private Adress adress;
}
