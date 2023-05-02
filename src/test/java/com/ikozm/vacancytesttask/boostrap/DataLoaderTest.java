package com.ikozm.vacancytesttask.boostrap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.ikozm.vacancytesttask.bootstrap.DataLoader;
import com.ikozm.vacancytesttask.model.Vacancy;
import com.ikozm.vacancytesttask.repository.VacancyRepository;
import com.ikozm.vacancytesttask.service.ArbeItNowService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class DataLoaderTest {

    @Mock
    private ArbeItNowService  arbeItNowService;
    @Mock
    private VacancyRepository vacancyRepository;
    @Mock
    private Logger            logger;

    @InjectMocks
    private DataLoader dataLoader;

    @Test
    public void testRun_noDataInRepository_shouldLoadData() {
        when(vacancyRepository.findAll()).thenReturn(Collections.emptyList());
        when(arbeItNowService.getVacancies(anyInt())).thenReturn(Collections.singleton(new Vacancy()));

        dataLoader.run();

        verify(arbeItNowService, times(DataLoader.PAGE_COUNT)).getVacancies(anyInt());
        verify(vacancyRepository).saveAll(anyCollection());
    }

    @Test
    public void testRun_dataInRepository_shouldNotLoadData() {
        when(vacancyRepository.findAll()).thenReturn(Collections.singletonList(new Vacancy()));

        dataLoader.run();

        verifyNoInteractions(arbeItNowService);
    }
}
