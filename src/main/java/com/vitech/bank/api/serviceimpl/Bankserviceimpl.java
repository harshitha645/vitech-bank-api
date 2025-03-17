package com.vitech.bank.api.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vitech.bank.api.dto.CustInfo;
import com.vitech.bank.api.dto.EmpDto;
import com.vitech.bank.api.model.Bankmodel;
import com.vitech.bank.api.repository.Bankrepo;
import com.vitech.bank.api.service.Bankservice;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Bankserviceimpl implements Bankservice {

	@Autowired
	public Bankrepo bankrepo;
    @Value("${emp.info.url}")
	private String empinfourl ;
	RestTemplate restTemplate = new RestTemplate();
	@Override
	public String createBankdata(Bankmodel bankmodel) {
		log.info("save bankdata : start");

		Optional<Bankmodel> existingBank = bankrepo.findByAccountNumAndBank(bankmodel.getAccountNum(),
				bankmodel.getBank());

		if (existingBank.isPresent()) {
			return "Account" + bankmodel.getAccountNum() + " already exists in the bank" + bankmodel.getBank();
		}
		bankrepo.save(bankmodel);
		return "Customer details saved successfully.Account number: " + bankmodel.getAccountNum();
	}

	@Override
	public String depositToAccount(Bankmodel bankmodel) {
		log.info("depositToAccount: start");

		// Validate input account number and bank
		if (bankmodel.getAccountNum() == null || bankmodel.getBank() == null) {
			return "Account Number and Bank are required fields.";
		}

		// Find the account by account number and bank
		Optional<Bankmodel> existingBank = bankrepo.findByAccountNumAndBank(bankmodel.getAccountNum(),
				bankmodel.getBank());
		if (existingBank.isEmpty()) {
			return "Account number " + bankmodel.getAccountNum() + " does not exist in the bank " + bankmodel.getBank();
		}

		// Update deposit amount if provided
		Bankmodel account = existingBank.get();
		if (bankmodel.getDepositAmount() != null) {
			int updatedAmount = (account.getDepositAmount() != null ? account.getDepositAmount() : 0)
					+ bankmodel.getDepositAmount();
			account.setDepositAmount(updatedAmount);
		}

		// Save the updated account details
		bankrepo.save(account);

		return "Amount deposit successfully done for the Account Number " + bankmodel.getAccountNum()
				+ " deposite amount " + bankmodel.getDepositAmount();
	}

	@Override
	public String getBalance(String bank, String type, Long accountNum, Integer atmPin) {
		// Mock logic: Ideally, fetch data from the database
		Optional<Bankmodel> bankModelOpt = bankrepo.findByBankAndTypeAndAccountNumAndAtmPin(bank, type, accountNum,
				atmPin);

		if (bankModelOpt.isPresent()) {
			return String.valueOf(bankModelOpt.get().getDepositAmount());
		} else {
			throw new IllegalArgumentException("Invalid details provided");
		}
	}

	@Override
	public String withdrawAmount(String bank, String type, Long accountNum, Integer atmPin,
			Integer amountToBeWithdraw) {
		Optional<Bankmodel> bankModelOpt = bankrepo.findByBankAndTypeAndAccountNumAndAtmPin(bank, type, accountNum,
				atmPin);

		if (bankModelOpt.isPresent()) {
			Bankmodel bankModel = bankModelOpt.get();
			int currentBalance = bankModel.getDepositAmount();

			if (amountToBeWithdraw > currentBalance) {
				throw new IllegalArgumentException("Insufficient balance");
			}

			bankModel.setDepositAmount(currentBalance - amountToBeWithdraw);
			bankrepo.save(bankModel);

			return String.format("Amount %d is debited. Your total balance is %d", amountToBeWithdraw,
					bankModel.getDepositAmount());
		} else {
			throw new IllegalArgumentException("Invalid details provided");
		}

	}

	@Override
	public CustInfo getcustomerDetails(Long accountNum) {
		log.info("Customer details fetched successfully");
		CustInfo custInfo = new CustInfo();
		EmpDto empDto = null;
		Optional<Bankmodel> dbResponse = bankrepo.findByAccountNum(accountNum);
		try{
			if(dbResponse.isPresent()) {
			Bankmodel bankDbResp = dbResponse.get();
			String aadharno = bankDbResp.getAadharno();
			if(!StringUtils.isEmpty(aadharno)) {
				String empurl= empinfourl +aadharno;
				//calling employee data api
				 empDto = restTemplate.getForObject(
			            empurl,
			            EmpDto.class);
				System.out.println("Employee response ="+empDto);
				custInfo.setAccountnumber(bankDbResp.getAccountNum());
				custInfo.setAdhar(aadharno);
				custInfo.setAddress(empDto.getAddress());
				custInfo.setEmail(empDto.getEmail());
				custInfo.setName(empDto.getName());
				custInfo.setPhone(empDto.getPhone());
			} else {
				log.info("Aadhar number is null no need to call employe api");
				custInfo.setMessage("Aadhar number is not found in customer api");
			}
			
			} else {
				custInfo.setMessage("Account number is not found in customer api");
			}
		}  catch (Exception ex) {
			log.error("exception occurred while calling eployee api-{}",ex);
		}
		return custInfo;
	}

	@Override
	public List<Bankmodel> getallcustomers() {
		log.info("Customer details fetched successfully");
		return bankrepo.findAll();
	}

	
}
