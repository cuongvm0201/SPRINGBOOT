package vn.techmaster.finalproject.repository;

import vn.techmaster.finalproject.model.ActiveCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActiveCodeRepo extends JpaRepository<ActiveCode,String> {
    
}
