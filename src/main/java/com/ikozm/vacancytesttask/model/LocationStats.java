package com.ikozm.vacancytesttask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LocationStats {

    private String location;

    private Long jobCount;
}
