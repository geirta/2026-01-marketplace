package ee.geir.marketplace.repository;

import ee.geir.marketplace.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {
}
