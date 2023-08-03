package com.overgo.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.overgo.demo.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	
}
