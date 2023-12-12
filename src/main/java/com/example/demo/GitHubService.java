package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class GitHubService {

    @Value("${github.token}")
    private String githubToken;  // Inject your GitHub token from application.properties or application.yml

    private static final String GITHUB_API_BASE_URL = "https://api.github.com";
    private static final String REPO_OWNER = "nishantggoel";
    private static final String REPO_NAME = "test";

    private final RestTemplate restTemplate;

    @Autowired
    public GitHubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void cloneRepository(String localPath) {
        String cloneEndpoint = GITHUB_API_BASE_URL + "/repos/" + REPO_OWNER + "/" + REPO_NAME + "/git/clone_token";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(cloneEndpoint, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String cloneUrlResponse = response.getBody();
            // Use cloneUrlResponse to perform the cloning using Git or any other tool of your choice
            System.out.println("Repository cloned to " + localPath);
        } else {
            System.err.println("Error cloning repository: " + response.getStatusCode());
        }
    }

    public void pushRepository(String localPath) {
        try {
            String[] command = {"git", "-C", localPath, "push", "origin", "master"};
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Repository pushed successfully");
            } else {
                System.err.println("Error pushing repository: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Handle the exception based on your application's requirements
        }
    }

    public void pullRepository(String localPath) {
        try {
            String[] command = {"git", "-C", localPath, "pull", "origin", "master"};
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Repository pulled successfully");
            } else {
                System.err.println("Error pulling repository: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Handle the exception based on your application's requirements
        }
    }

}
