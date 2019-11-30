package edu.kpi.brs.jobfinderstash.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kpi.brs.jobfinderstash.dao.JobPostChangelogDao;
import edu.kpi.brs.jobfinderstash.dao.JobPostDao;
import edu.kpi.brs.jobfinderstash.model.JobPost;
import edu.kpi.brs.jobfinderstash.model.JobPostChangelog;

@Service
public class LoggerService {

    private Date lastLogTime;

    @Autowired
    private JobPostChangelogDao jobPostChangelogDao;

    @Autowired
    private JobPostDao jobPostDao;

    @Autowired
    private ElasticRestClient elasticRestClient;

    @PostConstruct
    private void postConstruct() {
        lastLogTime = new Date();
    }

    public List<JobPostChangelog> getLastLogs(int seconds) {
        Date lastTime = new Date();
        System.out.println(lastTime.toString());
        lastTime.setTime(lastTime.getTime()-seconds*1000);
        System.out.println(lastTime.toString());
        return jobPostChangelogDao.getLastLogs(lastTime);
    }

    public List<Object> pushChangedJobPosts(int seconds) {
        List<JobPostChangelog> lastLogs = getLastLogs(seconds);
        List<JobPost> changedJobs = lastLogs
                .stream()
                .map(JobPostChangelog::getJobPost)
                .map(jobPost -> jobPostDao.findById(jobPost.getId()).orElse(null))
                .collect(Collectors.toList());

        return changedJobs
                .stream()
                .map(jobPost -> elasticRestClient.pushJobPost(jobPost))
                .collect(Collectors.toList());
    }
}
