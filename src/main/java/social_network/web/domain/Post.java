package social_network.web.domain;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    private User author;
    private String title;
    private String content;
    @ManyToMany
    private Collection<User> likes;

    @OneToMany(mappedBy = "replyTo")
    private Collection<Post> replies;

    @ManyToOne
    private Post replyTo;
    // Medias
}
