package vn.techmaster.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.finalproject.model.Bill;


@Repository
public interface BillRepo extends JpaRepository<Bill,String>{
    
}
