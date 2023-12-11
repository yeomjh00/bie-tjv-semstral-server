package social_network.web.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("nonplayable")
@Getter @Setter
public class NonplayableMedia extends Media{
    private Double height;
    private Double width;
}
