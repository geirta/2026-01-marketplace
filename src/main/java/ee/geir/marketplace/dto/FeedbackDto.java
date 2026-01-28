package ee.geir.marketplace.dto;

public record FeedbackDto(Long personReviewerId, Long personReviewedId, String message, Boolean positiveFb) {

}
