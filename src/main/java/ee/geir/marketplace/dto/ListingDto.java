package ee.geir.marketplace.dto;

public record ListingDto(Long authorId, String image, String title, String description, double price, Long categoryId) {
}