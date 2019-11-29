package edu.kpi.brs.jobfinderstash.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kpi.brs.jobfinderstash.dao.JobPostChangelogDao;
import edu.kpi.brs.jobfinderstash.model.JobPostChangelog;

@Service
public class LoggerService {

    private Date lastLogTime;

    @Autowired
    JobPostChangelogDao jobPostChangelogDao;

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

}
