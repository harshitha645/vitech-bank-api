package com.vitech.bank.api.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitech.bank.api.model.Bankmodel;
import com.vitech.bank.api.repository.Bankrepo;
import com.vitech.bank.api.service.Bankservice;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Bankserviceimpl implements Bankservice {
	
	@Autowired
	
	public Bankrepo bankrepo;

	@Override
	public String createBankdata(Bankmodel bankmodel) {
	    log.info("save bankdata : start");
	    
	    Optional<Bankmodel>existingBank = bankrepo.findByAccountNumAndBank(bankmodel.getAccountNum(),bankmodel.getBank());
	    
	    if(existingBank.isPresent()) {
	    	return "Account" + bankmodel.getAccountNum() + " already exists in the bank" + bankmodel.getBank();
	    }
		bankrepo.save(bankmodel);
	    return "Customer details saved successfully.Account number: "+bankmodel.getAccountNum();
	}

	@Override
    public String depositToAccount(Bankmodel bankmodel) {
        log.info("depositToAccount: start");

        // Validate input account number and bank
        if (bankmodel.getAccountNum() == null || bankmodel.getBank() == null) {
            return "Account Number and Bank are required fields.";
        }

        // Find the account by account number and bank
        Optional<Bankmodel> existingBank = bankrepo.findByAccountNumAndBank(bankmodel.getAccountNum(), bankmodel.getBank());
        if (existingBank.isEmpty()) {
            return "Account number " + bankmodel.getAccountNum() + " does not exist in the bank " + bankmodel.getBank();
        }

        // Update deposit amount if provided
        Bankmodel account = existingBank.get();
        if (bankmodel.getDepositAmount() != null) {
            int updatedAmount = (account.getDepositAmount() != null ? account.getDepositAmount() : 0) + bankmodel.getDepositAmount();
            account.setDepositAmount(updatedAmount);
        }

        // Save the updated account details
        bankrepo.save(account);

        return "Amount deposit successfully done for the Account Number " + bankmodel.getAccountNum()+ " deposite amount "+bankmodel.getDepositAmount();
        }
	 @Override
	    public String getBalance(String bank, String type, Long accountNum, Integer atmPin) {
	        // Mock logic: Ideally, fetch data from the database
		 Optional<Bankmodel> bankModelOpt = bankrepo.findByBankAndTypeAndAccountNumAndAtmPin(
	                bank, type, accountNum, atmPin);
	        
	        if (bankModelOpt.isPresent()) {
	            return String.valueOf(bankModelOpt.get().getDepositAmount());
	        } else {
	            throw new IllegalArgumentException("Invalid details provided");
	        }
	 }
	 @Override 
	   public String withdrawAmount(String bank, String type, Long accountNum, Integer atmPin, Integer amountToBeWithdraw) {
           Optional<Bankmodel> bankModelOpt = bankrepo.findByBankAndTypeAndAccountNumAndAtmPin(
        		   bank, type, accountNum, atmPin);
           
           if (bankModelOpt.isPresent()) {
               Bankmodel bankModel = bankModelOpt.get();
               int currentBalance = bankModel.getDepositAmount();

               if (amountToBeWithdraw > currentBalance) {
                   throw new IllegalArgumentException("Insufficient balance");
               }

               bankModel.setDepositAmount(currentBalance - amountToBeWithdraw);
               bankrepo.save(bankModel);
               
               return String.format("Amount %d is debited. Your total balance is %d", amountToBeWithdraw, bankModel.getDepositAmount());
           } else {
               throw new IllegalArgumentException("Invalid details provided");
           }
         


}
}

	        
	       