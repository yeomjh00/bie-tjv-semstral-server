package social_network.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social_network.web.controller.asset.PictureDto;
import social_network.web.controller.asset.PostDto;
import social_network.web.domain.Music;
import social_network.web.domain.Picture;
import social_network.web.domain.Post;
import social_network.web.repository.PostRepository;
import social_network.web.repository.media.PictureRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService implements CrudService<Post, Long> {


    private final PostRepository postRepository;
    private final PictureRepository pictureRepository;

    @Autowired
    public PostService(PostRepository postRepository,
                       PictureRepository pictureRepository) {
        this.postRepository = postRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Post save(Post post) {
        for(Picture photo: post.getPictures()){
            pictureRepository.save(photo);
        }
        if (post.getContent() == null
                || post.getTitle() == null
                || post.getContent().isEmpty()
                || post.getTitle().isEmpty()
                || post.getTitle().length() > 255){
            return Post.Dto2Post(PostDto.invalidTitleOrContent(), post.getAuthor());
        }
        postRepository.save(post);
        return post;
    }

    public boolean validateTitleAndContent(String title, String content){
        if (title == null || title.isEmpty() || title.length() > 255){
            return false;
        } else return content != null && !content.isEmpty();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Post findByIdOrThrow(Long id){
        Post p = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return p;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<PostDto> findAllByAuthorId(Long id){
        return findAll().stream()
                .filter(post -> post.getAuthor().getId().equals(id))
                .map(PostDto::Post2Dto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
        return;
    }

    public ResponseEntity<PostDto> editPostContentAndTitle(Long id,
                                                           PostDto form,
                                                           Music music){
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PostDto.postNotFound());
        } else if(!validateTitleAndContent(form.getTitle(), form.getContent())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PostDto.invalidTitleOrContent());
        }
        Post p = post.get();

        List<PictureDto> pictureDtos = Optional.ofNullable(form.getPictureDtos()).orElseGet(Collections::emptyList);
        List<Picture> pictures = updatePhotos(p, pictureDtos);

        p.setContent(form.getContent());
        p.setTitle(form.getTitle());
        p.setSong(music);
        p.setPictures(pictures);

        postRepository.save(p);
        return ResponseEntity.ok(PostDto.Post2Dto(p));
    }


    public List<PostDto> findLikedPostsByUserId(Long user_id){
        return findAll().stream()
                .filter(post -> post.getLikes().stream().anyMatch(user -> user.getId().equals(user_id)))
                .map(PostDto::Post2Dto)
                .toList();
    }

    public void deleteAll() {
        postRepository.deleteAll();
    }

    private List<Picture> updatePhotos(Post post, List<PictureDto> photos){
        for(Picture photo: post.getPictures()){
            pictureRepository.deleteById(photo.getId());
        }

        List<Picture> picturesToSaved = photos.stream().map(Picture::Dto2Picture).toList();

        for(Picture photo : picturesToSaved){
            pictureRepository.save(photo);
        }
        return picturesToSaved;
    }

    public void saveAllPicturesFromDto(List<PictureDto> pictureDtos) {
        List<Picture> photos = pictureDtos.stream().map(Picture::Dto2Picture).toList();
        for (Picture photo: photos){
            pictureRepository.save(photo);
        }
    }
}
