package com.ikozm.vacancytesttask.service;

import com.ikozm.vacancytesttask.model.Vacancy;

import java.util.Set;

public interface ArbeItNowService {

    /**
     * This method retrieves a set of vacancies from the ArbeItNow API for a given page number.
     *
     * @param page The page number to retrieve the vacancies from.
     * @return A set of vacancies retrieved from the ArbeItNow API for the given page number.
     */
    Set<Vacancy> getVacancies(int page);
}
