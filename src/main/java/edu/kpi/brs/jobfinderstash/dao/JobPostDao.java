package edu.kpi.brs.jobfinderstash.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.kpi.brs.jobfinderstash.model.JobPost;

@Repository
public interface JobPostDao extends JpaRepository<JobPost, Integer> {
}
