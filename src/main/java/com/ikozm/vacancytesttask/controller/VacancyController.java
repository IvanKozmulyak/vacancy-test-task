package com.ikozm.vacancytesttask.controller;

import com.ikozm.vacancytesttask.model.LocationStats;
import com.ikozm.vacancytesttask.model.Vacancy;
import com.ikozm.vacancytesttask.repository.VacancyRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**

 This controller handles HTTP requests related to vacancies.
 */
@RestController
@RequestMapping("/api/vacancies")
public class VacancyController {

    private final VacancyRepository vacancyRepository;

    public VacancyController(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    /**

     Returns a list of vacancies with pagination and sorting options.
     @param page the page number to return
     @param size the number of vacancies per page
     @param sortBy the field to sort by
     @return a list of vacancies
     */
    @GetMapping("/")
    public Page<Vacancy> getJobs(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "createdAt") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return vacancyRepository.findAll(pageable);
    }

    /**

     Returns a list of location statistics for vacancies.
     @return a list of location statistics
     */
    @GetMapping("/location-stats")
    public List<LocationStats> getLocationStats() {
        return vacancyRepository.getLocationStats();
    }



//     Returns a list of top 10 vacancies.
//     Currently commented due to lack of data from API
//    @GetMapping("/top")
//    public List<Vacancy> getTopJobs() {
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("views").descending());
//        return vacancyRepository.findAll(pageable).getContent();
//    }
}