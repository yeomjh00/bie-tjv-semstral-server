package social_network.web.domain;

import jakarta.persistence.*;
import lombok.*;
import social_network.web.controller.asset.PictureDto;

@Entity
@Table(name = "picture")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Picture{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pictureId")
    private Long id;
    @Column(length=512)
    private String uri;
    private Integer height;
    private Integer width;
    @ManyToOne
    private Post containedPost;

    public static Picture Dto2Picture(PictureDto pictureDto){
        return Picture.builder()
                .id(pictureDto.getId())
                .uri(pictureDto.getUri())
                .height(pictureDto.getHeight())
                .width(pictureDto.getWidth())
                .build();
    }

    public boolean equals(Picture picture){
        return this.id.equals(picture.getId())
                && this.uri.equals(picture.getUri())
                && this.height.equals(picture.getHeight())
                && this.width.equals(picture.getWidth());
    }
}
