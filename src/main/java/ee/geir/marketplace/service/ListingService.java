package ee.geir.marketplace.service;

import ee.geir.marketplace.dto.ListingDto;
import ee.geir.marketplace.entity.Category;
import ee.geir.marketplace.entity.Listing;
import ee.geir.marketplace.repository.CategoryRepository;
import ee.geir.marketplace.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {
    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private void validate(ListingDto listingDto) {
        if (listingDto.authorId() == null) {
            throw new RuntimeException("Cannot add a new listing without author ID");
        }
        if (listingDto.title() == null || listingDto.title().isBlank()) {
            throw new RuntimeException("Add a listing title");
        }
        if (listingDto.description() == null || listingDto.description().isBlank()) {
            throw new RuntimeException("Add a listing description");
        }
        if (listingDto.price() < 0) {
            throw new RuntimeException("Price must be bigger than zero.");
        }

        Category category = categoryRepository.findById(listingDto.categoryId()).orElse(null);
        if (category == null) {
            throw new RuntimeException("Category doesn't exist with id: " +  listingDto.categoryId());
        }
    }

//        private Long id;
//        private Long authorId;
//        private String image;
//        private String title;
//        private String description;
//        private double price;
//        private Long date;
//        private Category category;

    public List<Listing> saveListing(ListingDto listingDto) {
        validate(listingDto);
        Listing listing = new Listing();
        listing.setAuthorId(listingDto.authorId());
        listing.setImage(listingDto.image());
        listing.setTitle(listingDto.title());
        listing.setDescription(listingDto.description());
        listing.setPrice(listingDto.price());
        listing.setDate(System.currentTimeMillis());
        Category category = categoryRepository.findById(listingDto.categoryId()).orElse(null);
        listing.setCategory(category);
        listingRepository.save(listing);
        return listingRepository.findAll();
    }


}
