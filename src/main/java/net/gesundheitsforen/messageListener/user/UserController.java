package net.gesundheitsforen.messageListener.user;

import net.gesundheitsforen.messageListener.temperature.Temperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list")
    List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    // Save
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    User newUser(@Valid @RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    // Find
    @GetMapping("/{id}")
    User show(@PathVariable @Min(1) Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    /* Save or update
    @PutMapping("/update/{id}")
    User saveOrUpdate(@RequestBody User newUser, @PathVariable Long id) {

        return userRepository.findById(id)
                .map(x -> {
                    x.setName(newUser.getName());
                    x.setFirstname(newUser.getFirstname());
                    x.setEmail(newUser.getEmail());
                    return userRepository.save(x);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }
    */


    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

}

