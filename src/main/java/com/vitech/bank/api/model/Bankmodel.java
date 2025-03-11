package com.vitech.bank.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Long getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(Long accountNum) {
		this.accountNum = accountNum;
	}
	public Integer getAtmPin() {
		return atmPin;
	}
	public void setAtmPin(Integer atmPin) {
		this.atmPin = atmPin;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public Integer getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Integer depositAmount) {
		this.depositAmount = depositAmount;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Bankmodel [id=" + id + ", customerName=" + customerName + ", bank=" + bank + ", type=" + type
				+ ", branchName=" + branchName + ", accountNum=" + accountNum + ", atmPin=" + atmPin + ", ifscCode="
				+ ifscCode + ", depositAmount=" + depositAmount + ", mailId=" + mailId + "]";
	}
	
	public Bankmodel(Integer id, String customerName, String bank, String type, String branchName, Long accountNum,
			Integer atmPin, String ifscCode, Integer depositAmount, String mailId) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.bank = bank;
		this.type = type;
		this.branchName = branchName;
		this.accountNum = accountNum;
		this.atmPin = atmPin;
		this.ifscCode = ifscCode;
		this.depositAmount = depositAmount;
		this.mailId = mailId;
	}
	
	public Bankmodel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
