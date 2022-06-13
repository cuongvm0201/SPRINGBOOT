package vn.techmaster.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.bank.model.RateConfig;
@Repository
public interface RateConfigRepo extends JpaRepository<RateConfig,String> {
    
}
