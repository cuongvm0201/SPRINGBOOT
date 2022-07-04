package vn.techmaster.finalproject.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.finalproject.model.Contact;
import vn.techmaster.finalproject.repository.ContactRepo;
import vn.techmaster.finalproject.request.ContactRequest;

@Service
public class ContactService {
    @Autowired private ContactRepo contactRepo;
    public List<Contact> getAllMessage(){
        return contactRepo.findAll();
    }

    public Contact addNewMessage(ContactRequest contactRequest){
          String id = UUID.randomUUID().toString();
          Contact newContact = Contact.builder()
          .id(id)
          .fullname(contactRequest.getFullname())
          .email(contactRequest.getEmail())
          .phone(contactRequest.getPhone())
          .message(contactRequest.getMessage())
          .build();

          contactRepo.save(newContact);
          return newContact;
    }

    public Contact findInboxById(String inboxID){
        return contactRepo.findById(inboxID).get();
    }
}
