package social_network.web.controller.asset;

import lombok.*;
import social_network.web.domain.Post;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class PostDto {
    private Long id;
    private String authorUsername;
    private Long userId;
    private String title;
    private String content;
    private Long numberOfLikes;
    private List<PictureDto> pictureDtos;
    private MusicDto musicDto;

    public PostDto(Post post){
        this.id = post.getId();
        this.authorUsername = post.getAuthor().getUsername();
        this.userId = post.getAuthor().getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.numberOfLikes = (long) post.getLikes().size();
        this.pictureDtos = post.getPictureDtos();
        this.musicDto = MusicDto.Music2Dto(post.getSong());
    }

    public static PostDto Post2Dto(Post post){
        List<PictureDto> pictureDtos = Optional.of(post.getPictureDtos())
                .orElse(List.of());

        return PostDto.builder()
                .id(post.getId())
                .authorUsername(post.getAuthor().getUsername())
                .userId(post.getAuthor().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .pictureDtos(pictureDtos)
                .musicDto(MusicDto.Music2Dto(post.getSong()))
                .numberOfLikes((long) post.getLikes().size())
                .build();
    }

    public static PostDto Post2ShortDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .authorUsername(post.getAuthor().getUsername())
                .userId(post.getAuthor().getId())
                .title(post.getTitle())
                .content("")
                .numberOfLikes((long) post.getLikes().size())
                .build();
    }

    public static PostDto postNotFound(){
        return PostDto.builder()
                .id(-1L)
                .authorUsername("Post Not Found")
                .userId(-1L)
                .title("Post Not Found")
                .content("Post Not Found")
                .numberOfLikes(-1L)
                .build();
    }

    public static PostDto invalidTitleOrContent() {
        return PostDto.builder()
                .id(-1L)
                .authorUsername("Invalid Title or Content")
                .userId(-1L)
                .title("Invalid Title or Content")
                .content("Invalid Title or Content")
                .numberOfLikes(-1L)
                .build();
    }
}
