package com.frankefelipee.myissuertracker.issue;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
public class IssueLinkBuilder {

    private final IssueRepository issueRepository;

    public CollectionModel<EntityModel<Issue>> getIssuesListWithLinks() {

        List<EntityModel<Issue>> issues = issueRepository.findAll().stream()
                .map(issue -> EntityModel.of(
                        issue,
                        linkTo(methodOn(IssueController.class).oneIssue(issue.getId())).withSelfRel(),
                        linkTo(methodOn(IssueController.class).changeIssue(Issue.mapIssueToRequest(issue), issue.getId())).withRel("modify"),
                        linkTo(methodOn(IssueController.class).deleteIssue(issue.getId())).withRel("delete"),
                        linkTo(methodOn(IssueController.class).finishIssue(issue.getId())).withRel("complete"),
                        linkTo(methodOn(IssueController.class).allIssues()).withRel("issues")))
                .toList();

        return CollectionModel.of(issues, linkTo(methodOn(IssueController.class).allIssues()).withSelfRel());

    }

}
