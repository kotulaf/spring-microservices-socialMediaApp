package com.spring.restfulwebservices;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
    public EntityModel<User> retrieveOneUser(@PathVariable int id) {
        User user = service.findOne(id);                    // Assign found user to a variable

        if(user == null)
        {
            throw new UserNotFoundException("id-" + id);    // We pass in a message we want for our exception, it has to be created
        }

        EntityModel<User> model = EntityModel.of(user);     // Entity model wraps an object, which is our user and adds links to it

        WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers()); // we specify the retrieveAllUsers method and the framework picks up the URL from GetMapping
        model.add(linkToUsers.withRel("all-users"));

        return model;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id)        // when we don't return anything we automatically get Http status 200
    {
        User user = service.deleteById(id);

        if(user == null)
        {
            throw new UserNotFoundException("id-" + id);    // Here we use the same exception, if the user was not found then we throw an exception that they were not found
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {       // adding the valid annotation to tell spring that this RequestBody needs to be validated
        User savedUser = service.save(user);

        URI location =  ServletUriComponentsBuilder // first we take the uri we currently have and we append the new user id to it, and convert it to an URI
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedUser.getId())
        .toUri();

        return ResponseEntity.created(location).build();    // we are creating a new Response Entity with the created status and pass in the URI we just created, and then we build it ofc
    }
}
