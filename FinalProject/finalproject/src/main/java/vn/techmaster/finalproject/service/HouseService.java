package vn.techmaster.finalproject.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.techmaster.finalproject.exception.SearchException;
import vn.techmaster.finalproject.model.House;
import vn.techmaster.finalproject.model.Reverse;
import vn.techmaster.finalproject.repository.HouseRepo;
import vn.techmaster.finalproject.request.SearchRequest;

@Service
public class HouseService {
    @Autowired
    private HouseRepo houseRepo;
    public List<House> showAllHouse() {
        return houseRepo.findAll();
    }
    
    public Page<House> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.houseRepo.findAll(pageable);
       }

    public List<House> findHouseBySearch(SearchRequest searchRequest) {
        LocalDate date1 = LocalDate.parse(searchRequest.checkin());
        LocalDate date2 = LocalDate.parse(searchRequest.checkout());
        if (date2.compareTo(LocalDate.now()) < 0
                || date1.compareTo(date2) > 0) {
            throw new SearchException("Ngày đã chọn không hợp lệ");
        }

        List<House> allHouse = showAllHouse();
        List<House> filterHouse = new ArrayList<>();
        for (int i = 0; i < allHouse.size(); i++) {
            if (allHouse.get(i).getCity().equals(searchRequest.city()) && allHouse.get(i).getPrice() >= searchRequest.price() ) {
             
                    if (allHouse.get(i).getReverses().isEmpty()) {
                        filterHouse.add(allHouse.get(i));
                    }
                    for (int j = 0; j < allHouse.get(i).getReverses().size(); j++) {
                        Reverse a = allHouse.get(i).getReverses().get(j);

                        if ((date1.compareTo(date2) <= 0 && date2.isBefore(a.getCheckin()))) {
                            filterHouse.add(allHouse.get(i));
                        }
                        if ((date1.compareTo(a.getCheckout()) > 0 && date2.compareTo(date1) >= 0)) {
                            filterHouse.add(allHouse.get(i));
                        }
                    }
                
            }

            if ( searchRequest.city() == null && allHouse.get(i).getPrice() >= searchRequest.price()) {

                if (allHouse.get(i).getReverses().isEmpty()) {
                    filterHouse.add(allHouse.get(i));
                }
                for (int j = 0; j < allHouse.get(i).getReverses().size(); j++) {
                    Reverse a = allHouse.get(i).getReverses().get(j);

                    if ((date1.compareTo(date2) <= 0 && date2.isBefore(a.getCheckin()))) {
                        filterHouse.add(allHouse.get(i));
                    }
                    if ((date1.compareTo(a.getCheckout()) > 0 && date2.compareTo(date1) >= 0)) {
                        filterHouse.add(allHouse.get(i));
                    }
                }
            }
        }

        return filterHouse;
    }

    public House add(House house) {
        String id = UUID.randomUUID().toString();
        house.setId(id);
        houseRepo.save(house);
        return house;
    }

    public void updateLogo(String id, String logo_main) {
        Optional<House> house = findById(id);
        house.get().setLogo_main(logo_main);
        houseRepo.save(house.get());
    }

    public void updateLogo1(String id, String logo_sub1) {
        Optional<House> house = findById(id);
        house.get().setLogo_sub_main1(logo_sub1);
        houseRepo.save(house.get());
    }

    public void updateLogo2(String id, String logo_sub2) {
        Optional<House> house = findById(id);
        house.get().setLogo_sub_main2(logo_sub2);
        houseRepo.save(house.get());
    }

    public void updateLogo3(String id, String logo_sub3 ) {
        Optional<House> house = findById(id);
        house.get().setLogo_sub_main3(logo_sub3);
        houseRepo.save(house.get());
    }

    public Optional<House> findById(String id) {
        return houseRepo.findById(id);
    }


    public void edit(House houseEdit) {
        
        houseRepo.save(houseEdit);
    }

    public void deleteById(String id) {
        houseRepo.deleteById(id);
    }
}
