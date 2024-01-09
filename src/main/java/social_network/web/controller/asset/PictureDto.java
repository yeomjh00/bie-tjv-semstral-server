package social_network.web.controller.asset;

import lombok.*;
import social_network.web.domain.Picture;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class PictureDto {
    private Long id;
    private String uri;
    private Integer height;
    private Integer width;
    private Long postId;

    public static PictureDto Picture2Dto(Picture picture){
        return PictureDto.builder()
                .id(picture.getId())
                .uri(picture.getUri())
                .height(picture.getHeight())
                .width(picture.getWidth())
                .build();
    }

    public boolean equals(PictureDto picture){
        return this.id.equals(picture.getId())
                && this.uri.equals(picture.getUri())
                && this.height.equals(picture.getHeight())
                && this.width.equals(picture.getWidth());
    }
}
