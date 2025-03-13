package com.vitech.bank.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitech.bank.api.model.Bankmodel;

@Repository
public interface Bankrepo extends JpaRepository<Bankmodel, Integer> { // Changed generic type to Long

    Optional<Bankmodel> findByAccountNumAndBank(Long accountNum, String bank); // Corrected method name casing

	Optional<Bankmodel> findByBankAndTypeAndAccountNumAndAtmPin(String bank, String type, long long1, int int1);
	
	
}

 