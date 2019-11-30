package edu.kpi.brs.jobfinderstash.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import edu.kpi.brs.jobfinderstash.model.JobPost;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ElasticRestClient {

    private static final String UPDATE_JOB_POST = "/job_search/job_post/${job_post_id}";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${elasticsearch.url}")
    private String elasticsearchUrl;

    public Object pushJobPost(JobPost jobPost) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<JobPost> entity = new HttpEntity<>(jobPost, headers);


        String restUrl = UPDATE_JOB_POST
                .replace("${job_post_id}", Integer.toString(jobPost.getId()));
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(elasticsearchUrl + restUrl);

        ResponseEntity responseEntity;

        try {
            responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod
                    .POST, entity, JobPost.class);
        } catch (Exception e) {
            log.error("Could not push job post: " + jobPost.toString() + ". Reason: " + e.getMessage());
            return null;
        }

        log.info("Pushed job post to elastic: " + jobPost.toString());
        return responseEntity.getBody();
    }
}
