package social_network.web.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social_network.web.controller.asset.UserRegisterForm;
import social_network.web.domain.Post;
import social_network.web.domain.User;
import social_network.web.repository.PostRepository;
import social_network.web.repository.UserJpaRepository;
import social_network.web.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserService implements CrudService<User, Long> {
    private final UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        duplicateAndNullCheck(user);
        userStatusValidCheck(user);
        userRepository.save(user);
        return user;
    }

    public User saveFromDto(UserRegisterForm userRegisterForm){
        var user = new User();
        user.setUsername(userRegisterForm.getUsername());
        user.setRealName(userRegisterForm.getRealName());
        user.setIntroduction(userRegisterForm.getIntroduction());
        user.setUserStatus(userRegisterForm.getUserStatus());
        return save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User findByIdOrThrow(Long id){
        User u = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return u;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<Post> findMyPostsByUserId(Long id){ return userRepository.findMyPostsById(id); }

    public List<Post> findLikedPostsByUserId(Long id){ return userRepository.findLikedPostsById(id); }

    @Override
    public void deleteById(Long id) { userRepository.deleteById(id); }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByRealName(String realName) {
        return userRepository.findByRealName(realName);
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public void duplicateAndNullCheck(User user){
        if (user == null){
            throw new NullPointerException("User is null");
        }
        else if (existsByUsername(user.getUsername())){
            log.error("Username already exists" + user.getUsername());
            log.error("repository result: ");
            log.error(userRepository.findByUsername(user.getUsername()).get().getUsername());
            throw new IllegalArgumentException("Username already exists");
        }
        else if (user.getUsername().length() > 255 ||
                user.getUsername().isEmpty()){
            throw new IllegalArgumentException("Username must be less than 255 characters and not empty");
        }
        else if (user.getRealName().length() > 255 ||
                user.getRealName().isEmpty()){
            throw new IllegalArgumentException("Real name must be less than 255 characters and not empty");
        }
    }

    public void userStatusValidCheck(User user){
        if (user.getUserStatus().equals("trial") ||
                user.getUserStatus().equals("membership")){
            return;
        }
        else{
            throw new IllegalArgumentException("User status must be trial or membership");
        }
    }
}
