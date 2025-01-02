package com.sb.rolebased.security.dto;

import com.sb.rolebased.facility.Entity.Flat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FlatMeterLinkResponseDto {
    private Flat flat;
    private int assignedMeters;
    private int unassignedMeters;
}