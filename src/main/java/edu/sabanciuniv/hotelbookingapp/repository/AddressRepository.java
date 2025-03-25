package edu.sabanciuniv.hotelbookingapp.repository;

import edu.sabanciuniv.hotelbookingapp.model.Address;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	
	@Query("SELECT DISTINCT a.city FROM Address a")
	List<String> findDistinctCities();
	}
