package social_network.web.domain;

import jakarta.persistence.*;
import lombok.*;
import social_network.web.controller.asset.PostDto;

import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name ="author")
    private User author;
    private String title;
    private String content;
    @ManyToMany
    private List<User> likes;

    @OneToMany
    private List<Picture> pictures;

    @OneToMany
    private List<Music> songs;

    public boolean equals(Post post){
        return this.id.equals(post.getId())
                && this.author.equals(post.getAuthor())
                && this.title.equals(post.getTitle())
                && this.content.equals(post.getContent());
    }

    public static Post Dto2Post(PostDto postDto, User author){
        return Post.builder()
                .id(postDto.getId())
                .author(author)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
    }
//    public ArrayList<User> getLikes(){
//        return (ArrayList<User>) this.likes;
//    }
//
//    public void setLikes(ArrayList<User> likes){
//        this.likes = (List<User>) likes;
//    }
}
