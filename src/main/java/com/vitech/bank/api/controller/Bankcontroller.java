package com.vitech.bank.api.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitech.bank.api.model.Bankmodel;
import com.vitech.bank.api.service.Bankservice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/bankdetails")

public class Bankcontroller {
	
	@Autowired
	public Bankservice bankservice;
	@PostMapping("/createAccount")
	public ResponseEntity<String> insertBankdata(@RequestBody Bankmodel bankmodel) {
        log.info("insertBankdata:: input recived - {}", bankmodel);
     try {
		String result=	bankservice.createBankdata(bankmodel);
		return ResponseEntity.ok(result);
     }catch(Exception e) {
		log.error("Error occured: {}" , e.getMessage());
		return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
     }
	}
	 @PutMapping("/deposit")
	    public ResponseEntity<String> depositAmount(@RequestBody Bankmodel bankmodel) {
	        log.info("depositAmount:: input received - {}" , bankmodel);

	        try {
	            String result = bankservice.depositToAccount(bankmodel);
	            return ResponseEntity.ok(result);
	        } catch (Exception e) {
	            log.error("Error occurred: {}" ,e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("An error occurred while processing the deposit request.");
	        }

   }
     @GetMapping("/checkbalance")
     public ResponseEntity<String> checkBalance(
    		 @RequestParam String bank,
             @RequestParam String type,
             @RequestParam Long accountNum,
             @RequestParam Integer atmPin) {
         
         try {
             String balance = bankservice.getBalance(bank, type, accountNum, atmPin);
             String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
             String response = String.format("{\"status\" : \"Your total balance as on %s is %s\"}", date, balance);
             return ResponseEntity.ok(response);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching balance");
         }
     }
     @PostMapping("/withDraw")
     public ResponseEntity<String> withdrawAmount(
             @RequestParam String bank,
             @RequestParam String type,
             @RequestParam Long accountNum,
             @RequestParam Integer atmPin,
             @RequestParam Integer amountToBeWithdraw) {
         
         try {
             String response = bankservice.withdrawAmount(bank, type, accountNum, atmPin, amountToBeWithdraw);
             return ResponseEntity.ok(response);
         } catch (IllegalArgumentException e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the withdrawal");
         }
     }
}