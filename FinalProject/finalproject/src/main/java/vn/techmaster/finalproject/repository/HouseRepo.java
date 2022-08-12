package vn.techmaster.finalproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.finalproject.model.City;
import vn.techmaster.finalproject.model.House;

@Repository
public interface HouseRepo extends JpaRepository<House,String> {
    Page<House> findAllByCity(City city, Pageable pageable);
    
    
}
