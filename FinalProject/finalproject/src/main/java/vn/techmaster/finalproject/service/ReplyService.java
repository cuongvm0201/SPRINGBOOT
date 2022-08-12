package vn.techmaster.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.finalproject.model.Reply;
import vn.techmaster.finalproject.repository.ReplyRepo;
import vn.techmaster.finalproject.request.ReplyRequest;

@Service
public class ReplyService {
    @Autowired private ReplyRepo replyRepo;
    @Autowired private EmailService emailService;
    public List<Reply> getAllReply(){
        return replyRepo.findAll();
    }

    public Reply creatNewReply(Reply reply, String adminID, String customerEmail){
        replyRepo.save(reply);
        emailService.replyInbox(customerEmail, "Cảm ơn bạn đã gửi tin nhắn", reply.getMessage());
        return reply;
    }
}
