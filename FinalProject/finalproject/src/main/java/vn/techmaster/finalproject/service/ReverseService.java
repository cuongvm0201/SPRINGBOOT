package vn.techmaster.finalproject.service;



import java.util.ArrayList;
import java.util.List;


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

    public List<Reverse> findAllReverseByUserID(String userID){
         List<Reverse> allReverse = reverseRepo.findAll();   
         List<Reverse> reverseByUser = new ArrayList<>();
         for(int i = 0; i < allReverse.size();i++){
            if(allReverse.get(i).getUser().getId().equals(userID)){
                reverseByUser.add(allReverse.get(i));
            }
         }
        return reverseByUser;
    }


}
