package com.vitech.bank.api.dto;

import lombok.Data;

@Data
public class CustInfo {
	private Long accountnumber;
	private String name; 
	private String address;
	private String adhar;
	private String phone; 
	private String email ;
	private String message;

}
