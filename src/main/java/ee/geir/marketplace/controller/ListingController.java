package ee.geir.marketplace.controller;

import ee.geir.marketplace.dto.ListingDto;
import ee.geir.marketplace.entity.Listing;
import ee.geir.marketplace.repository.ListingRepository;
import ee.geir.marketplace.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private ListingService listingService;

    @GetMapping("listings")
    public List<Listing> getListings(){
        return listingRepository.findAll();
    }

    @PostMapping("listings")
    public List<Listing> addListing(@RequestBody ListingDto listingDto) {
        listingService.saveListing(listingDto);
        return listingRepository.findAll();
    }

    @DeleteMapping("listings/{id}")
    public List<Listing> deleteListing(@PathVariable Long id){
        listingRepository.deleteById(id);
        return listingRepository.findAll();
    }
}
