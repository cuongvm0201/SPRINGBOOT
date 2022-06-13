package vn.techmaster.bank.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.bank.model.RateConfig;
import vn.techmaster.bank.repository.RateConfigRepo;

@Service
public class RateConfigService {
    @Autowired RateConfigRepo rateConfigRepo;

    public ConcurrentHashMap<Long,Double> getAllRateConfig(){
        List<RateConfig> allRate = rateConfigRepo.findAll();
        ConcurrentHashMap<Long,Double> newMapRate = new ConcurrentHashMap<>();
        for(int i = 0 ; i <allRate.size();i++){
            newMapRate.put(allRate.get(i).getMonths(),allRate.get(i).getRate());
        }
        return newMapRate;
    }

    public Double findRateByMonth(Long month){
        Double rateValue = getAllRateConfig().get(month);

        return rateValue;
    }
}
