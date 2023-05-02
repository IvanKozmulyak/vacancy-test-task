package com.ikozm.vacancytesttask.scheduler;

import com.ikozm.vacancytesttask.converter.StringListConverter;
import com.ikozm.vacancytesttask.model.Vacancy;
import com.ikozm.vacancytesttask.repository.VacancyRepository;
import com.ikozm.vacancytesttask.service.ArbeItNowService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**

 This class is responsible for scheduling the process of retrieving vacancies
 and saving them to the database. It uses the ArbeItNowService to retrieve the
 vacancies and the VacancyRepository to save them to the database.
 */
@Component
public class VacanciesScheduler {

    private static final Logger              log = LoggerFactory.getLogger(VacanciesScheduler.class);
    private final        ArbeItNowService    arbeItNowService;
    private final        VacancyRepository   vacancyRepository;
    private final        StringListConverter stringListConverter;


    public VacanciesScheduler(ArbeItNowService arbeItNowService, VacancyRepository vacancyRepository, StringListConverter stringListConverter) {
        this.arbeItNowService = arbeItNowService;
        this.vacancyRepository = vacancyRepository;
        this.stringListConverter = stringListConverter;
    }

    /**
     * This method is scheduled to run at the specified interval and retrieves vacancies
     * from the ArbeItNowService and saves them to the database using the VacancyRepository.
     */
    @Scheduled(cron = "${app.scheduler.vacancies.process.cron}")
    public void processVacancies() {
        Set<Vacancy> vacancies = arbeItNowService.getVacancies(1);
        try {
            // Save vacancies to the database
            vacancies.forEach(vacancy -> vacancyRepository.upsert(vacancy, stringListConverter.convertToDatabaseColumn(vacancy.getTags()), stringListConverter.convertToDatabaseColumn(vacancy.getJobTypes())));
        } catch (Exception e) {
            log.error("Error saving vacancies to the database: " + e.getMessage(), e);
        }
    }
}
