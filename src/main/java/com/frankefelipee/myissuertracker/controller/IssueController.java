package com.frankefelipee.myissuertracker.controller;

import com.frankefelipee.myissuertracker.entity.Issue;
import com.frankefelipee.myissuertracker.request.IssueRequest;
import com.frankefelipee.myissuertracker.service.IssueService;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private final IssueService issueService;

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<Issue>>> allIssues() {

        CollectionModel<EntityModel<Issue>> allIssues = issueService.getAllIssues();
        return ResponseEntity.ok(allIssues);

    }

    @GetMapping("/search/{id}")
    public ResponseEntity<EntityModel<Issue>> oneIssue(@PathVariable String id) {

        EntityModel<Issue> issue = issueService.getOneIssue(id);
        return ResponseEntity.ok(issue);

    }

    @GetMapping("/search/pending")
    public ResponseEntity<CollectionModel<EntityModel<Issue>>> pendingIssues() {

        CollectionModel<EntityModel<Issue>> pendingIssues = issueService.filterPendingIssues();
        return ResponseEntity.ok(pendingIssues);

    }

    @GetMapping("/search/finished")
    public ResponseEntity<CollectionModel<EntityModel<Issue>>> finishedIssues() {

        CollectionModel<EntityModel<Issue>> pendingIssues = issueService.filterFinishedIssues();
        return ResponseEntity.ok(pendingIssues);

    }

    @PostMapping("/create")
    public ResponseEntity<EntityModel<Issue>> createIssue(@Valid @RequestBody IssueRequest issueRequest) {

        Issue issue = new Issue();
        issue.setSalesForce(issueRequest.salesForce());
        issue.setDescription(issueRequest.description());
        issue.setTicket(issueRequest.ticket());
        EntityModel<Issue> issueEntityModel = issueService.generateNewIssue(issue);
        return ResponseEntity.status(HttpStatus.CREATED).body(issueEntityModel);

    }

    @PatchMapping("/mark_done/{id}")
    public ResponseEntity<EntityModel<Issue>> finishIssue(@PathVariable String id) {

        return ResponseEntity.ok(issueService.markIssueAsDone(id));

    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<EntityModel<Issue>> changeIssue(@Valid @RequestBody IssueRequest issueRequest, @PathVariable String id) {

        Issue issue = new Issue();
        issue.setSalesForce(issueRequest.salesForce());
        issue.setDescription(issueRequest.description());
        issue.setTicket(issueRequest.ticket());
        EntityModel<Issue> changedIssue = issueService.modifyIssue(issue, id);
        return ResponseEntity.ok(changedIssue);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteIssue(@PathVariable String id) {

        issueService.deleteIssueById(id);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllIssues() {

        issueService.deleteAllIssues();
        return ResponseEntity.noContent().build();

    }

}
