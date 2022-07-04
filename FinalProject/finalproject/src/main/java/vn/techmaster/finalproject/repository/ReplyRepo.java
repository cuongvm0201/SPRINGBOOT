package vn.techmaster.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.finalproject.model.Reply;

@Repository
public interface ReplyRepo extends JpaRepository<Reply,String> {
    
}
