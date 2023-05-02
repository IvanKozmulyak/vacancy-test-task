package com.ikozm.vacancytesttask.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class VacancyView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long          id;
    @ManyToOne
    @JoinColumn(name = "vacancy_id", nullable = false)
    @ToString.Exclude
    private Vacancy vacancy;
    @Column(nullable = false)
    private LocalDateTime viewedAt;

    public VacancyView(Vacancy vacancy) {
        this.vacancy = vacancy;
        this.viewedAt = LocalDateTime.now();
    }

    @JsonBackReference
    public Vacancy getVacancy() {
        return vacancy;
    }
}