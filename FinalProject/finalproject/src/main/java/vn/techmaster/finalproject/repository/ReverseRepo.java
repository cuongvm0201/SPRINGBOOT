package vn.techmaster.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.finalproject.model.Reverse;

@Repository
public interface ReverseRepo extends JpaRepository<Reverse,String> {
    
}
