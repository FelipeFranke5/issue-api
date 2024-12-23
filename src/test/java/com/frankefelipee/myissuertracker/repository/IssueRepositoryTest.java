package com.frankefelipee.myissuertracker.repository;

import com.frankefelipee.myissuertracker.entity.Issue;
import com.frankefelipee.myissuertracker.exception.IssueNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class IssueRepositoryTest {

    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void save_ReturnsSavedIssue() {

        // Arrange
        Issue issue = Issue.builder()
                .description("Test description")
                .ticket("12345678")
                .salesForce("12345678").build();

        // Act
        Issue savedIssue = issueRepository.save(issue);

        // Test behaviour or Assert
        Assertions.assertNotNull(savedIssue);
        Assertions.assertNotNull(savedIssue.getId());

    }

    @Test
    public void findAll_ZeroIssues_ReturnsEmptyList() {

        // Arrange
        List<Issue> issues = issueRepository.findAll();

        // Assert
        Assertions.assertEquals(0, issues.size());

    }

    @Test
    public void findAll_FilledIssues_ReturnsListOfCreatedIssues() {

        // Arrange
        Issue issue1 = Issue.builder()
                .description("Test description 1")
                .ticket("12345678")
                .salesForce("12345678").build();
        Issue issue2 = Issue.builder()
                .description("Test description 2")
                .ticket("123456789")
                .salesForce("123456789").build();

        // Act
        issueRepository.save(issue1);
        issueRepository.save(issue2);
        List<Issue> issues = issueRepository.findAll();

        // Assert
        Assertions.assertNotNull(issue1);
        Assertions.assertNotNull(issue2);
        Assertions.assertEquals(2, issues.size());

    }

    @Test
    public void findByDone_CompletedIssues_ReturnsListOfCreatedIssues() {

        // Arrange
        Issue issue1 = Issue.builder()
                .description("Test description 1")
                .ticket("12345678")
                .done(true)
                .salesForce("12345678").build();
        Issue issue2 = Issue.builder()
                .description("Test description 2")
                .ticket("123456789")
                .done(true)
                .salesForce("123456789").build();

        // Act
        issueRepository.save(issue1);
        issueRepository.save(issue2);
        List<Issue> pendingIssues = issueRepository.findByDone(false);
        List<Issue> finishedIssues = issueRepository.findByDone(true);

        // Assert
        Assertions.assertNotNull(issue1);
        Assertions.assertNotNull(issue2);
        Assertions.assertTrue(pendingIssues.isEmpty());
        Assertions.assertFalse(finishedIssues.isEmpty());

    }

    @Test
    public void findByDone_FilledIssues_ReturnsListOfCreatedIssues() {

        // Arrange
        Issue issue1 = Issue.builder()
                .description("Test description 1")
                .ticket("12345678")
                .salesForce("12345678").build();
        Issue issue2 = Issue.builder()
                .description("Test description 2")
                .ticket("123456789")
                .salesForce("123456789").build();

        // Act
        issueRepository.save(issue1);
        issueRepository.save(issue2);
        List<Issue> pendingIssues = issueRepository.findByDone(false);
        List<Issue> finishedIssues = issueRepository.findByDone(true);

        // Assert
        Assertions.assertNotNull(issue1);
        Assertions.assertNotNull(issue2);
        Assertions.assertFalse(pendingIssues.isEmpty());
        Assertions.assertTrue(finishedIssues.isEmpty());

    }

    @Test
    public void findById_ReturnsIssueObject() {

        // Arrange
        Issue issue1 = Issue.builder()
                .description("Test description 1")
                .ticket("12345678")
                .salesForce("12345678").build();
        Issue issue2 = Issue.builder()
                .description("Test description 2")
                .ticket("123456789")
                .salesForce("123456789").build();

        // Act
        issueRepository.save(issue1);
        issueRepository.save(issue2);
        Optional<Issue> issue1QueryResult = issueRepository.findById(issue1.getId());
        Optional<Issue> issue2QueryResult = issueRepository.findById(issue2.getId());

        // Assert
        Assertions.assertNotNull(issue1);
        Assertions.assertNotNull(issue2);
        Assertions.assertEquals(2, issueRepository.findAll().size());
        Assertions.assertTrue(issue1QueryResult.isPresent());
        Assertions.assertTrue(issue2QueryResult.isPresent());

    }

    @Test
    public void findById_InvalidId_ReturnsNotPresentOptional() {

        // Arrange
        // .. Skipped ..

        // Act
        Optional<Issue> issueQueryResult = issueRepository.findById("1234");

        // Assert
        Assertions.assertFalse(issueQueryResult.isPresent());

    }

    @Test
    public void findById_NonExistingIssue_ThrowsIssueNotFoundException() {

        // Arrange
        Issue issue1 = Issue.builder()
                .description("Test description 1")
                .ticket("12345678")
                .salesForce("12345678").build();
        Issue issue2 = Issue.builder()
                .description("Test description 2")
                .ticket("123456789")
                .salesForce("123456789").build();
        String randomId1 = UUID.randomUUID().toString();
        String randomId2 = UUID.randomUUID().toString();

        // Act
        issueRepository.save(issue1);
        issueRepository.save(issue2);
        Optional<Issue> issueQuery1 = issueRepository.findById(randomId1);
        Optional<Issue> issueQuery2 = issueRepository.findById(randomId2);
        Optional<Issue> issueQuery3 = issueRepository.findById(issue1.getId());
        Optional<Issue> issueQuery4 = issueRepository.findById(issue2.getId());

        // Assert
        Assertions.assertNotNull(issue1);
        Assertions.assertNotNull(issue2);
        Assertions.assertEquals(2, issueRepository.findAll().size());
        Assertions.assertFalse(issueQuery1.isPresent());
        Assertions.assertFalse(issueQuery2.isPresent());
        Assertions.assertTrue(issueQuery3.isPresent());
        Assertions.assertTrue(issueQuery4.isPresent());
        Assertions.assertThrows(
                IssueNotFoundException.class,
                () -> issueRepository.findById(randomId1).orElseThrow(() -> new IssueNotFoundException(randomId1))
        );
        Assertions.assertDoesNotThrow(() -> issueRepository.findById(issue1.getId()).orElseThrow(
                () -> new IssueNotFoundException(issue1.getId())
        ));

    }

        @Test
        public void deleteById_RemovesIssueFromDatabase() {

            // Arrange
            Issue issue1 = Issue.builder()
                    .description("Test description 1")
                    .ticket("12345678")
                    .salesForce("123456").build();

            // Act
            Issue savedIssue = issueRepository.save(issue1);
            issueRepository.deleteById(savedIssue.getId());
            List<Issue> issues = issueRepository.findAll();

            // Assert
            Assertions.assertNotNull(savedIssue);
            Assertions.assertEquals(0, issues.size());

        }

        @Test
        public void deleteAll_RemovesAllIssuesFromDatabase() {

            // Arrange
            Issue issue1 = Issue.builder()
                    .description("Test description 1")
                    .ticket("12345678")
                    .salesForce("123456").build();
            Issue issue2 = Issue.builder()
                    .description("Test description 2")
                    .ticket("123456789")
                    .salesForce("123456789").build();

            // Act
            issueRepository.save(issue1);
            issueRepository.save(issue2);
            List<Issue> issues = issueRepository.findAll();
            Assertions.assertFalse(issues.isEmpty());
            issueRepository.deleteAll();

            // Assert
            Assertions.assertNotNull(issue1);
            Assertions.assertNotNull(issue2);
            List<Issue> emptyIssues = issueRepository.findAll();
            Assertions.assertTrue(emptyIssues.isEmpty());

        }

        @Test
        public void update_ChangesIssue() {

            // Arrange
            Issue issue1 = Issue.builder()
                    .description("Test description 1")
                    .ticket("12345678")
                    .salesForce("123456").build();

            // Act
            issueRepository.save(issue1);

            // Initial Assertions (Before change)
            Assertions.assertNotNull(issue1);
            Assertions.assertEquals("Test description 1", issue1.getDescription());
            Assertions.assertEquals("12345678", issue1.getTicket());
            Assertions.assertEquals("123456", issue1.getSalesForce());

            // Perform update
            issue1.setDescription("Another Description");
            issue1.setTicket("0123456");
            issue1.setSalesForce("012345678");
            issue1.setDone(true);
            Issue changedIssue = issueRepository.save(issue1);

            // Assert (After change)
            Assertions.assertEquals("Another Description", changedIssue.getDescription());
            Assertions.assertEquals("0123456", changedIssue.getTicket());
            Assertions.assertEquals("012345678", changedIssue.getSalesForce());
            Assertions.assertTrue(changedIssue.isDone());

        }

}
