package com.vitech.bank.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table (name ="Customer")

public class Bankmodel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Corrected to use proper strategy
    private Integer id;
	private String customerName;
	private String bank;
	private String type;
	private String branchName;
	private Long accountNum;
	private Integer atmPin;
	private String ifscCode;
	private Integer depositAmount;
	private String mailId;
	private String aadharno;

	

}
