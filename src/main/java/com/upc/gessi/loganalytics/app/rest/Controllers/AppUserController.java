package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.models.AppUser;
import com.upc.gessi.loganalytics.app.domain.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class AppUserController {

    @Autowired
    private AppUserRepository userRepository;

    @GetMapping
    public List<AppUser> findAllUsers() {
        Iterable<AppUser> userIterable = userRepository.findAll();
        List<AppUser> userList = new ArrayList<>();
        userIterable.forEach(userList::add);
        return userList;
    }

    @GetMapping("/{id}")
    public AppUser findUserById(@PathVariable(value = "id") long id) {
        Optional<AppUser> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            return user;
        }
        return null;
    }

    @PostMapping
    public AppUser saveUser(@Validated @RequestBody AppUser user) {
        return userRepository.save(user);
    }
}
