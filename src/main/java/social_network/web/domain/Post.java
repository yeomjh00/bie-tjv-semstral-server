package social_network.web.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Optional;

// post_id / author_user_id / content / replyTo_post_id / title
@Entity
@Table(name = "posts")
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
    private Collection<User> likes;

    @OneToMany
    private Collection<Picture> pictures;

    @OneToMany
    private Collection<Music> songs;
}
