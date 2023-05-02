package com.ikozm.vacancytesttask.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ikozm.vacancytesttask.model.Vacancy;
import com.ikozm.vacancytesttask.service.impl.ArbeItNowServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class ArbeItNowServiceImplTest {

    @Mock
    private RestTemplate restTemplateMock;

    @InjectMocks
    private ArbeItNowServiceImpl service;

    @Test
    public void getVacancies_shouldReturnSetOfVacancies_whenApiResponseIsValid() {
        // Arrange
        ResponseEntity<String> response = new ResponseEntity<>(
                "{\"data\":[{\"id\":1,\"title\":\"Job 1\",\"description\":\"Description 1\",\"location\":\"Location 1\"}]}",
                HttpStatus.OK);
        Mockito.when(restTemplateMock.getForEntity(Mockito.anyString(), Mockito.eq(String.class)))
               .thenReturn(response);

        // Act
        Set<Vacancy> vacancies = service.getVacancies(1);

        // Assert
        assertThat(vacancies).hasSize(1);
        Vacancy vacancy = vacancies.iterator().next();
        assertThat(vacancy.getId()).isEqualTo(1);
        assertThat(vacancy.getTitle()).isEqualTo("Job 1");
        assertThat(vacancy.getDescription()).isEqualTo("Description 1");
        assertThat(vacancy.getLocation()).isEqualTo("Location 1");
    }

    @Test
    public void getVacancies_shouldReturnEmptySet_whenApiResponseHasNoVacancies() {
        // Arrange
        ResponseEntity<String> response = new ResponseEntity<>(
                "{\"data\":[]}",
                HttpStatus.OK);
        Mockito.when(restTemplateMock.getForEntity(Mockito.anyString(), Mockito.eq(String.class)))
                .thenReturn(response);

        // Act
        Set<Vacancy> vacancies = service.getVacancies(1);

        // Assert
        assertThat(vacancies).isEmpty();
    }

    @Test
    public void getVacancies_shouldReturnEmptySet_whenApiResponseIsInvalid() {
        // Arrange
        ResponseEntity<String> response = new ResponseEntity<>("invalid json", HttpStatus.OK);
        Mockito.when(restTemplateMock.getForEntity(Mockito.anyString(), Mockito.eq(String.class)))
                .thenReturn(response);

        // Act
        Set<Vacancy> vacancies = service.getVacancies(1);

        // Assert
        assertThat(vacancies).isEmpty();
    }

    @Test
    public void getVacancies_shouldReturnEmptySet_whenApiReturnsError() {
        // Arrange
        ResponseEntity<String> response = new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        Mockito.when(restTemplateMock.getForEntity(Mockito.anyString(), Mockito.eq(String.class)))
                .thenReturn(response);

        // Act
        Set<Vacancy> vacancies = service.getVacancies(1);

        // Assert
        assertThat(vacancies).isEmpty();
    }

}
