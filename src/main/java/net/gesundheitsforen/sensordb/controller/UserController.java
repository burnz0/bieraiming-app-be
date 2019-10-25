package net.gesundheitsforen.sensordb.controller;

import net.gesundheitsforen.sensordb.exception.ResourceNotFoundException;
import net.gesundheitsforen.sensordb.model.User;
import net.gesundheitsforen.sensordb.payload.UserIdentityAvailability;
import net.gesundheitsforen.sensordb.payload.UserProfile;
import net.gesundheitsforen.sensordb.payload.UserSummary;
import net.gesundheitsforen.sensordb.repository.UserRepository;
import net.gesundheitsforen.sensordb.security.CurrentUser;
import net.gesundheitsforen.sensordb.security.UserPrincipal;
import net.gesundheitsforen.sensordb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
                currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(),
                user.getName(), user.getCreatedAt(), user.getRoles());

        return userProfile;
    }

    @GetMapping("/users/list")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfile> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserProfile> result = new ArrayList<>();

        for (User user : userList) {
            UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(),
                    user.getName(), user.getCreatedAt(), user.getRoles());
            result.add(userProfile);
        }

        return result;
    }
}
