package ee.geir.marketplace.controller;

import ee.geir.marketplace.dto.FeedbackDto;
import ee.geir.marketplace.entity.Feedback;
import ee.geir.marketplace.repository.FeedbackRepository;
import ee.geir.marketplace.repository.PersonRepository;
import ee.geir.marketplace.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("feedback")
    public List<Feedback> getFeedbacks(){
        return feedbackRepository.findAll();
    }

    @GetMapping("feedback/{personId}")
    public List<Feedback> getPersonFeedback(@PathVariable Long personId){
        personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Can't find person with id: " + personId));
        return feedbackRepository.findByPersonReviewedIdOrderByDateDesc(personId);
    }

    @PostMapping("feedback")
    public List<Feedback> saveFeedback(@RequestBody FeedbackDto feedbackDto){
        return feedbackService.saveFeedback(feedbackDto);
    }
}
