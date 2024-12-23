package com.frankefelipee.myissuertracker.repository;

import com.frankefelipee.myissuertracker.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {

    List<Issue> findByDone(boolean done);

}
