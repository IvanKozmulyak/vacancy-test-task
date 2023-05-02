package com.ikozm.vacancytesttask.repository;

import com.ikozm.vacancytesttask.model.VacancyView;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyViewRepository extends JpaRepository<VacancyView, Long> {}
