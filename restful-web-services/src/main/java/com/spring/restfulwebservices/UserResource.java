package com.spring.restfulwebservices;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import User.User;
import User.UserNotFoundException;

@RestController
public class UserResource {

    @Autowired 
    private UserDaoService service;
    
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveOneUser(@PathVariable int id) {
        User user = service.findOne(id);                    // Assign found user to a variable

        if(user == null)
        {
            throw new UserNotFoundException("id-" + id);    // We pass in a message we want for our exception, it has to be created
        }

        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User savedUser = service.save(user);

        URI location =  ServletUriComponentsBuilder // first we take the uri we currently have and we append the new user id to it, and convert it to an URI
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedUser.getId())
        .toUri();

        return ResponseEntity.created(location).build();    // we are creating a new Response Entity with the created status and pass in the URI we just created, and then we build it ofc
    }
}
