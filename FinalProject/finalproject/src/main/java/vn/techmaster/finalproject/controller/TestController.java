package vn.techmaster.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.finalproject.model.City;
import vn.techmaster.finalproject.model.House;
import vn.techmaster.finalproject.repository.HouseRepo;
import vn.techmaster.finalproject.repository.PagingAndSortingRepository;

@RestController
@RequestMapping("api/v1/test")
public class TestController {
    @Autowired private HouseRepo houseRepo;

    @GetMapping("/city/{pageNumber}")
    public ResponseEntity<?> testfindCity(@RequestParam City city, @PathVariable("pageNumber") int pageNumber){
        Page<House> page ;
        Pageable pageable = PageRequest.of(pageNumber-1,3);
        page = houseRepo.findAllByCity(city, pageable);
        List<House> abc = page.getContent();
        return  ResponseEntity.ok().body(abc);
    }
}
