package ee.geir.marketplace.service;

import ee.geir.marketplace.dto.FeedbackDto;
import ee.geir.marketplace.entity.Feedback;
import ee.geir.marketplace.entity.Person;
import ee.geir.marketplace.repository.FeedbackRepository;
import ee.geir.marketplace.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private PersonRepository personRepository;

    private void validate(FeedbackDto feedbackDto) {
        if (feedbackDto.personReviewerId() == null) {
            throw new RuntimeException("Cannot leave a feedback without Reviewer ID");
        }
        if (feedbackDto.personReviewedId() == null) {
            throw new RuntimeException("Cannot leave a feedback without reviewed person ID");
        }
        if (feedbackDto.personReviewedId() == feedbackDto.personReviewerId()) {
            throw new RuntimeException("Cannot leave a feedback to yourself");
        }
        Person dbPersonReviewer = personRepository.findById(feedbackDto.personReviewerId()).orElse(null);
        if (dbPersonReviewer == null) {
            throw new RuntimeException("Cannot leave a feedback without existing reviewer person object");
        }
        Person dbPersonReviewed = personRepository.findById(feedbackDto.personReviewedId()).orElse(null);
        if (dbPersonReviewed == null) {
            throw new RuntimeException("Cannot leave a feedback without existing reviewer person object");
        }
        if (feedbackDto.positiveFb() == null) {
            throw new RuntimeException("Feedback must be marked as positive or negative");
        }
        if (feedbackDto.message() == null || feedbackDto.message().isBlank()) {
            throw new RuntimeException("Cannot leave a blank feedback");
        }
    }

    public List<Feedback> saveFeedback(FeedbackDto feedbackDto) {
        validate(feedbackDto);
        Feedback feedback = new Feedback();
        feedback.setPersonReviewerId(feedbackDto.personReviewerId());
        feedback.setPersonReviewedId(feedbackDto.personReviewedId());
        feedback.setMessage(feedbackDto.message());
        feedback.setPositiveFb(feedbackDto.positiveFb());
        feedback.setDate(System.currentTimeMillis());
        feedbackRepository.save(feedback);
        return feedbackRepository.findAll();
    }
}
