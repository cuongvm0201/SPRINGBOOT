package vn.techmaster.finalproject.service;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import vn.techmaster.finalproject.model.Reverse;
import vn.techmaster.finalproject.repository.HouseRepo;
import vn.techmaster.finalproject.repository.ReverseRepo;
import vn.techmaster.finalproject.repository.UserRepo;


@Service
public class ReverseService {
    @Autowired ReverseRepo reverseRepo;
    @Autowired HouseRepo houseRepo;
    @Autowired UserRepo userRepo;
    public Reverse creatNewReverse(Reverse reverse){

        return reverseRepo.save(reverse);
    }
}
