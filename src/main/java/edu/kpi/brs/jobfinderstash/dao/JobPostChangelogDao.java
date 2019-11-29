package edu.kpi.brs.jobfinderstash.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.kpi.brs.jobfinderstash.model.JobPostChangelog;

@Repository
public interface JobPostChangelogDao extends JpaRepository<JobPostChangelog, Integer> {
    @Query(value = "SELECT * FROM job_post_changelog "
            + "WHERE "
            + "(job_post_changelog.modification_time > :sql_last_value "
            + "AND job_post_changelog.modification_time < NOW()) "
            + "ORDER BY job_post_changelog.modification_time ASC",
    nativeQuery = true)
    List<JobPostChangelog> getLastLogs(@Param("sql_last_value") Date lastValue);
}