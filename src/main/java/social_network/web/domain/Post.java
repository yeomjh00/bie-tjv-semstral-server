package social_network.web.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;
import social_network.web.controller.asset.PictureDto;
import social_network.web.controller.asset.PostDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// post_id / author_user_id / content / replyTo_post_id / title
@Entity
@Table(name = "posts")
@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="author_id")
    private User author;
    private String title;
    private String content;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "posts_likes",
            joinColumns = @JoinColumn(name = "liked_posts_post_id"),
            inverseJoinColumns = @JoinColumn(name = "likes_userid")
    )
    List<User> likes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Picture> pictures;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "song_id", nullable = true)
    private Music song;

    public List<Long> getPictureIds(){
        return Optional.of(pictures.stream()
                .map(Picture::getId)
                .toList())
                .orElse(Collections.emptyList());
    }

    public List<PictureDto> getPictureDtos(){
        return Optional.of(pictures.stream()
                .map(PictureDto::Picture2Dto)
                .toList())
                .orElse(Collections.emptyList());
    }

    public Long getSongId(){
        return Optional.ofNullable(song)
                .map(Music::getId)
                .orElse(-1L);
    }

    public boolean equals(Post post){
        return this.id.equals(post.getId())
                && this.author.equals(post.getAuthor())
                && this.title.equals(post.getTitle())
                && this.content.equals(post.getContent());
    }

    public static Post Dto2Post(PostDto postDto, User author){
        Music music = postDto.getMusicDto() == null ? null : Music.Dto2Music(postDto.getMusicDto());
        return Post.builder()
                .id(postDto.getId())
                .author(author)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .pictures(postDto.getPictureDtos().stream()
                                .map(Picture::Dto2Picture)
                                .collect(Collectors.toList())
                )
                .song(music)
                .build();
    }
}
