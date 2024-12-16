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

    public CollectionModel<EntityModel<Issue>> getIssuesListWithLinks(List<Issue> issueList) {

        List<EntityModel<Issue>> issues = issueList.stream()
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

    public EntityModel<Issue> getIssueWithLinks(String id, Issue issue) {

        return EntityModel.of(
                issue,
                linkTo(methodOn(IssueController.class).oneIssue(id)).withSelfRel(),
                linkTo(methodOn(IssueController.class).changeIssue(Issue.mapIssueToRequest(issue), id)).withRel("modify"),
                linkTo(methodOn(IssueController.class).deleteIssue(id)).withRel("delete"),
                linkTo(methodOn(IssueController.class).finishIssue(id)).withRel("complete"),
                linkTo(methodOn(IssueController.class).allIssues()).withRel("issues"));

    }

    public EntityModel<Issue> getCreatedIssueWithLinks(Issue issueCreated) {

        return EntityModel.of(
                issueCreated,
                linkTo(methodOn(IssueController.class).oneIssue(issueCreated.getId())).withSelfRel(),
                linkTo(methodOn(IssueController.class).changeIssue(Issue.mapIssueToRequest(issueCreated), issueCreated.getId())).withRel("modify"),
                linkTo(methodOn(IssueController.class).deleteIssue(issueCreated.getId())).withRel("delete"),
                linkTo(methodOn(IssueController.class).finishIssue(issueCreated.getId())).withRel("complete"),
                linkTo(methodOn(IssueController.class).allIssues()).withRel("issues"));

    }

    public EntityModel<Issue> getModifiedIssueWithLinks(Issue issueModified) {

        return EntityModel.of(
                issueModified,
                linkTo(methodOn(IssueController.class).oneIssue(issueModified.getId())).withSelfRel(),
                linkTo(methodOn(IssueController.class).changeIssue(Issue.mapIssueToRequest(issueModified), issueModified.getId())).withRel("modify"),
                linkTo(methodOn(IssueController.class).deleteIssue(issueModified.getId())).withRel("delete"),
                linkTo(methodOn(IssueController.class).finishIssue(issueModified.getId())).withRel("complete"),
                linkTo(methodOn(IssueController.class).allIssues()).withRel("issues"));

    }

}
