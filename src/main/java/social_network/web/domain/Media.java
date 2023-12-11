package social_network.web.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Entity
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public abstract class Media {
    @Id
    @GeneratedValue
    @Column(name = "media_id")
    private Long id;
    private String uri;
    private String description;
    private Date createdDate;
}

