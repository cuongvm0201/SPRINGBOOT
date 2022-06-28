package vn.techmaster.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.finalproject.model.Discount;

@Repository
public interface DiscountRepo extends JpaRepository<Discount,String> {
    
}
