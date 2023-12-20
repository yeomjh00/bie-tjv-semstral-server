package social_network.web.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "picture")
@Getter @Setter
public class Picture{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pictureId")
    private Long id;
    private String uri;
    private String title;
    private String description;
    private Double height;
    private Double width;
}
