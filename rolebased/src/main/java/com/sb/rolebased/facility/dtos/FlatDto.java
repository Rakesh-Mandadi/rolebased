package com.sb.rolebased.facility.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class FlatDto {
    private Long flatNumber;
    private int noOfMeters;
    
    private List<Long> meterTypeIds;
}

	    
	    
	    
//	    public FlatDto(Long flatId, Long flatNumber, Integer noOfMeters) {
//	        this.flatId = flatId;
//	        this.flatNumber = flatNumber;
//	        this.noOfMeters = noOfMeters;
//	    }
//}
