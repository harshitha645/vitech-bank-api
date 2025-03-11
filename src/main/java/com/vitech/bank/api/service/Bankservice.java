package com.vitech.bank.api.service;

import org.springframework.stereotype.Service;

import com.vitech.bank.api.model.Bankmodel;

@Service

public interface Bankservice {
	
	
	String createBankdata(Bankmodel bankmodel);

	String depositToAccount(Bankmodel bankmodel);

	String getBalance(String bank, String type, Long accountNum, Integer atmPin);

	String withdrawAmount(String bank, String type, Long accountNum, Integer atmPin, Integer amountToBeWithdraw);
	
	

}
