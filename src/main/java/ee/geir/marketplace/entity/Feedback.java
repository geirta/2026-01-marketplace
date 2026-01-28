package ee.geir.marketplace.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long personReviewerId;
    private Long personReviewedId;
    private String message;
    private Boolean positiveFb;
    private Long date;
}


