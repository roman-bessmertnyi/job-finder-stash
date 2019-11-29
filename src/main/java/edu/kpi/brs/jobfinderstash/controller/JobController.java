package edu.kpi.brs.jobfinderstash.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.kpi.brs.jobfinderstash.dao.JobPostDao;
import edu.kpi.brs.jobfinderstash.model.JobPost;
import edu.kpi.brs.jobfinderstash.model.JobPostChangelog;
import edu.kpi.brs.jobfinderstash.service.LoggerService;

@RestController
@RequestMapping("/api/v1")
public class JobController {

    @Autowired
    private JobPostDao jobPostDao;

    @Autowired
    private LoggerService loggerService;

    @GetMapping("jobs")
    public List<JobPost> getAllJobPosts() {
        return jobPostDao.findAll();
    }

    @GetMapping("logs/{seconds}")
    public List<JobPostChangelog> getLastLogs(@PathVariable final int seconds) {
        return loggerService.getLastLogs(seconds);
    }
}
