package social_network.web.controller.asset;

import lombok.*;
import social_network.web.domain.Post;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class PostDto {
    private Long id;
    private String authorUsername;
    private String title;
    private String content;
    private Long numberOfLikes;

    public PostDto(Post post){
        this.id = post.getId();
        this.authorUsername = post.getAuthor().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.numberOfLikes = (long) post.getLikes().size();
    }

    public static PostDto Post2Dto(Post post){
        Long id = post == null ? -1 : post.getId();
        return PostDto.builder()
                .id(id)
                .authorUsername(post.getAuthor().getUsername())
                .title(post.getTitle())
                .content(post.getContent())
                .numberOfLikes((long) post.getLikes().size())
                .build();
    }
}
