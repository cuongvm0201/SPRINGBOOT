package vn.techmaster.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.finalproject.model.Contact;
@Repository
public interface ContactRepo extends JpaRepository<Contact,String> {
    
}
