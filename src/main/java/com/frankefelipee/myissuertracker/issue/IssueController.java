package com.frankefelipee.myissuertracker.issue;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestController
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private final IssueService issueService;

    @GetMapping("/all")
    public ResponseEntity<IssueResponse> allIssues() {

        CollectionModel<EntityModel<Issue>> allIssues = issueService.getAllIssues();
        IssueResponse issueResponse = new IssueResponse(
                false,
                "successfull",
                "Query Successfull",
                null,
                allIssues
        );

        return ResponseEntity.ok(issueResponse);

    }

    @GetMapping("/search/{id}")
    public ResponseEntity<EntityModel<Issue>> oneIssue(@PathVariable String id) {

        EntityModel<Issue> issue = issueService.getOneIssue(id);
        return ResponseEntity.ok(issue);

    }

    @PostMapping("/create")
    ResponseEntity<EntityModel<Issue>> createIssue(@Valid @RequestBody IssueRequest issueRequest) {

        Issue issue = new Issue();
        issue.setSalesForce(issueRequest.salesForce());
        issue.setDescription(issueRequest.description());
        issue.setTicket(issueRequest.ticket());
        EntityModel<Issue> issueEntityModel = issueService.generateNewIssue(issue);
        return ResponseEntity.status(HttpStatus.CREATED).body(issueEntityModel);

    }

    @PatchMapping("/mark_done/{id}")
    ResponseEntity<EntityModel<Issue>> finishIssue(@PathVariable String id) {

        EntityModel<Issue> issue = issueService.markIssueAsDone(id);

        if (issue != null) {
            return ResponseEntity.ok(issue);
        }

        throw new IssueAlreadyDoneException();

    }

    @PutMapping("/modify/{id}")
    ResponseEntity<IssueResponse> changeIssue(@Valid @RequestBody IssueRequest issueRequest, @PathVariable String id) {



    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteIssue(@PathVariable String id) {

        try {

            issueService.deleteIssueById(id);
            return ResponseEntity.noContent().build();

        } catch (IssueNotFoundException issueNotFoundException) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }

    }

}
