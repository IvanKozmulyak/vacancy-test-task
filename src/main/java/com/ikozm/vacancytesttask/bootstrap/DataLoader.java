package com.ikozm.vacancytesttask.bootstrap;

import com.ikozm.vacancytesttask.model.Vacancy;
import com.ikozm.vacancytesttask.repository.VacancyRepository;
import com.ikozm.vacancytesttask.service.ArbeItNowService;

import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log        = LoggerFactory.getLogger(DataLoader.class);
    public static final  int    PAGE_COUNT = 10;

    private final ArbeItNowService  arbeItNowService;
    private final VacancyRepository vacancyRepository;
    private final Set<Vacancy>      vacancies = new HashSet<>();

    public DataLoader(ArbeItNowService arbeItNowService, VacancyRepository vacancyRepository) {
        this.arbeItNowService = arbeItNowService;
        this.vacancyRepository = vacancyRepository;
    }

    @Override
    public void run(String... args) {
        if (IterableUtils.toList(vacancyRepository.findAll()).isEmpty()) {
            loadData();
        }
    }

    private void loadData() {
        log.info("Started loading data");

        try {
            IntStream.rangeClosed(1, PAGE_COUNT)
                     .mapToObj(arbeItNowService::getVacancies)
                     .flatMap(Collection::stream)
                     .forEach(vacancies::add);

            vacancyRepository.saveAll(vacancies);
            log.info("Loaded data: {}", vacancies);
        } catch (Exception e) {
            log.error("Failed to load data", e);
        }
    }
}
