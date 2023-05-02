package com.ikozm.vacancytesttask.scheduler;

import com.ikozm.vacancytesttask.converter.StringListConverter;
import com.ikozm.vacancytesttask.model.Vacancy;
import com.ikozm.vacancytesttask.repository.VacancyRepository;
import com.ikozm.vacancytesttask.service.ArbeItNowService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

public class VacanciesSchedulerTest {

    private VacanciesScheduler vacanciesScheduler;

    @Mock
    private ArbeItNowService arbeItNowService;

    @Mock
    private VacancyRepository vacancyRepository;

    @Mock
    private StringListConverter stringListConverter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        vacanciesScheduler = new VacanciesScheduler(arbeItNowService, vacancyRepository, stringListConverter);
    }

    @Test
    public void processVacancies_withVacancies_shouldSaveVacanciesToDatabase() {
        Set<Vacancy> vacancies = new HashSet<>();
        Vacancy vacancy = new Vacancy();
        vacancy.setTitle("Test Vacancy");
        vacancies.add(vacancy);

        when(arbeItNowService.getVacancies(1)).thenReturn(vacancies);

        vacanciesScheduler.processVacancies();

        verify(vacancyRepository, times(1)).upsert(eq(vacancy), any(), any());
    }

    @Test
    public void processVacancies_withNoVacancies_shouldDoNothing() {
        when(arbeItNowService.getVacancies(1)).thenReturn(Collections.emptySet());

        vacanciesScheduler.processVacancies();

        verify(vacancyRepository, times(0)).upsert(any(), any(), any());
    }
}
