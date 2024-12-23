package com.frankefelipee.myissuertracker.service;

import com.frankefelipee.myissuertracker.exception.IssueAlreadyDoneException;
import com.frankefelipee.myissuertracker.exception.IssueContainsSameDataException;
import com.frankefelipee.myissuertracker.exception.IssueNotFoundException;
import com.frankefelipee.myissuertracker.entity.Issue;
import com.frankefelipee.myissuertracker.link.IssueLinkBuilder;
import com.frankefelipee.myissuertracker.repository.IssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueLinkBuilder linkBuilder;

    public CollectionModel<EntityModel<Issue>> getAllIssues() {

        List<Issue> issueList = issueRepository.findAll();
        return linkBuilder.getIssuesListWithLinks(issueList);

    }

    public EntityModel<Issue> getOneIssue(String id) {

        Issue issue = issueRepository.findById(id).orElseThrow(() -> new IssueNotFoundException(id));
        return linkBuilder.getIssueWithLinks(id, issue);

    }

    public EntityModel<Issue> generateNewIssue(Issue issue) {

        Issue issueCreated = issueRepository.save(issue);
        return linkBuilder.getCreatedIssueWithLinks(issueCreated);

    }

    public EntityModel<Issue> modifyIssue(Issue payloadIssue, String id) {

        Issue currentIssue = issueRepository.findById(id).orElseThrow(() -> new IssueNotFoundException(id));

        if (
                payloadIssue.getDescription().equals(currentIssue.getDescription()) &&
                        payloadIssue.getTicket().equals(currentIssue.getTicket()) &&
                        payloadIssue.getSalesForce().equals(currentIssue.getSalesForce())
        ) {

            throw new IssueContainsSameDataException();

        }

        currentIssue.setDescription(payloadIssue.getDescription());
        currentIssue.setTicket(payloadIssue.getTicket());
        currentIssue.setSalesForce(payloadIssue.getSalesForce());
        return linkBuilder.getModifiedIssueWithLinks(issueRepository.save(currentIssue));

    }

    public EntityModel<Issue> markIssueAsDone(String id) {

        Issue issueFound = issueRepository.findById(id).orElseThrow(() -> new IssueNotFoundException(id));

        if (!issueFound.isDone()) {

            issueFound.setDone(true);
            Issue modifiedIssue = issueRepository.save(issueFound);
            return linkBuilder.getIssueWithLinks(id, modifiedIssue);

        }

        throw new IssueAlreadyDoneException();

    }

    public void deleteIssueById(String id) {

        issueRepository.deleteById(id);

    }

    public void deleteAllIssues() {

        issueRepository.deleteAll();

    }

    public CollectionModel<EntityModel<Issue>> filterPendingIssues() {

        List<Issue> finishedIssues = issueRepository.findByDone(false);
        return linkBuilder.getIssuesListWithLinks(finishedIssues);

    }

    public CollectionModel<EntityModel<Issue>> filterFinishedIssues() {

        List<Issue> finishedIssues = issueRepository.findByDone(true);
        return linkBuilder.getIssuesListWithLinks(finishedIssues);

    }

}
