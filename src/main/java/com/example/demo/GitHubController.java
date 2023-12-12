package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/github")
public class GitHubController {

    private final GitHubService gitHubService;

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @PostMapping("/clone")
    public String cloneRepository() {
        gitHubService.cloneRepository("/path/to/local/repository");
        return "success"; // Redirect to a success page
    }

    @PostMapping("/push")
    public String pushRepository() {
        gitHubService.pushRepository("/path/to/local/repository");
        return "success"; // Redirect to a success page
    }

    @PostMapping("/pull")
    public String pullRepository() {
        gitHubService.pullRepository("/path/to/local/repository");
        return "success"; // Redirect to a success page

    }
}