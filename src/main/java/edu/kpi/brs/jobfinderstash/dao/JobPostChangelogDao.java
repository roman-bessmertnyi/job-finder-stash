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
    @Query(value = "SELECT c FROM JobPostChangelog c " +
            "JOIN FETCH c.jobPost "
            + "WHERE "
            + "(c.modificationTime > :sql_last_value "
            + "AND c.modificationTime < NOW()) "
            + "ORDER BY c.modificationTime ASC")
    List<JobPostChangelog> getLastLogs(@Param("sql_last_value") Date lastValue);
}