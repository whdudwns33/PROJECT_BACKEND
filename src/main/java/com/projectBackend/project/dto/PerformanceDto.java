package com.projectBackend.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerformanceDto {
    private Long performanceId;
    private String performanceName;
    private String performer;
    private String venue;
    private String detailVenue;
    private String performanceDate;
    private int price;
    private String description;
    private int seatCount;
    private String performanceImage;


}
