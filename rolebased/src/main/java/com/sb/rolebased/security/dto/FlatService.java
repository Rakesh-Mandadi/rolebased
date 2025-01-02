package com.sb.rolebased.security.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.rolebased.facility.dtos.FlatDto;
import com.sb.rolebased.facility.repository.FlatRepository;

@Service
public class FlatService {

    @Autowired
    private FlatRepository flatRepository;

//    public List<FlatDto> getFlatsWithMeters() {
//        return flatRepository.findFlatWithMeterCount();
//    }
    
    
}
