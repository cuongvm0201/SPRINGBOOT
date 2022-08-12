package vn.techmaster.finalproject.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
import vn.techmaster.finalproject.request.AdminSearchRequest;
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

    public Page<House> findHouseBySearch(SearchRequest searchRequest,Pageable pageable ) {
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
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), filterHouse.size());
        Page<House> page = new PageImpl<>(filterHouse.subList(start, end), pageable,filterHouse.size());
        
        return page;
    }

    public List<House> filterByCity(String city){
        List<House> allHouse = houseRepo.findAll();
        List<House> needHouse = new ArrayList<>();
        for (int i = 0; i < allHouse.size(); i++) {
            if(allHouse.get(i).getCity().toString().equals(city)){
                needHouse.add(allHouse.get(i));
            }
        }
        return needHouse;
    }


    public House add(House house) {
        String id = UUID.randomUUID().toString();
        house.setId(id);
        house.setView(0L);
        house.setRever(0L);
        houseRepo.save(house);
        return house;
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

    public List<House> sortHouse(){
        List<House> currentListHouse = houseRepo.findAll();
        Collections.sort(currentListHouse, new Comparator<House>() {
            @Override
            public int compare(House o1, House o2) {
                return o2.getView().compareTo(o1.getView());
            }
        });
        List<House> sortedListByView = new ArrayList<>(currentListHouse);
        return sortedListByView;
    }

    public List<House> filterHouseByAdmin(AdminSearchRequest adminSearchRequest) {
        List<House> houses = houseRepo.findAll();
        if(adminSearchRequest.getPrice() == null && adminSearchRequest.getCity() == null ){
            return houses;
        }
        if(adminSearchRequest.getPrice() == null){
            return houses.stream()
                .filter(house -> 
                 house.getCity().equals(adminSearchRequest.getCity()))
                .collect(Collectors.toList());
        }
        return houses.stream()
                .filter(house -> 
                (house.getPrice() >= adminSearchRequest.getPrice()
             && house.getCity().equals(adminSearchRequest.getCity())))
                .collect(Collectors.toList());
    }
}
