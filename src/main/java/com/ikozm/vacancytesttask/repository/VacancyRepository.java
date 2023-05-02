package com.ikozm.vacancytesttask.repository;

import com.ikozm.vacancytesttask.model.LocationStats;
import com.ikozm.vacancytesttask.model.Vacancy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    /**
     * Upserts an {@link Vacancy} entity into the database.
     *
     * @param vacancy  The {@link Vacancy} entity to upsert.
     * @param tags     The tags associated with the vacancy.
     * @param jobTypes The job types associated with the vacancy.
     */
    @Transactional
    @Modifying
    @Query(value = "MERGE INTO vacancy V USING (VALUES " +
            "(:#{#vacancy.slug}, :#{#vacancy.title}, :#{#vacancy.companyName}, " +
            ":#{#vacancy.description}, :#{#vacancy.location}, :#{#vacancy.createdAt}, :#{#vacancy.remote}, " +
            ":#{#vacancy.url}, :tags, :jobTypes)) AS new_values " +
            "(slug, title, company_name, description, location, created_at, remote, url, tags, job_types) " +
            "ON V.url = new_values.url " +
            "WHEN MATCHED THEN UPDATE SET " +
            "V.slug = new_values.slug, " +
            "V.title = new_values.title, " +
            "V.company_name = new_values.company_name, " +
            "V.description = new_values.description, " +
            "V.location = new_values.location, " +
            "V.created_at = new_values.created_at, " +
            "V.remote = new_values.remote, " +
            "V.tags = new_values.tags, " +
            "V.job_types = new_values.job_types " +
            "WHEN NOT MATCHED THEN " +
            "INSERT (slug, title, company_name, description, location, created_at, remote, url, tags, job_types) " +
            "VALUES (new_values.slug, new_values.title, new_values.company_name, " +
            "new_values.description, new_values.location, new_values.created_at, new_values.remote, " +
            "new_values.url, new_values.tags, new_values.job_types)", nativeQuery = true)
    void upsert(Vacancy vacancy, @Param("tags") String tags, @Param("jobTypes") String jobTypes);

    /**
     * Returns a list of {@link LocationStats} objects containing location and vacancies count.
     *
     * @return A list of {@link LocationStats} objects.
     */
    @Query(value = "SELECT new com.ikozm.vacancytesttask.model.LocationStats(location, COUNT(*) as job_count) " +
            "FROM Vacancy " +
            "GROUP BY location " +
            "ORDER BY job_count DESC")
    List<LocationStats> getLocationStats();
}
