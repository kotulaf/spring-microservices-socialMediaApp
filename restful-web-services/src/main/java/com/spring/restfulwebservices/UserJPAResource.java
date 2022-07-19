package com.spring.restfulwebservices;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import User.UserNotFoundException;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class UserJPAResource {
    @Autowired
    private UserRepository repo;
    
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {
        return repo.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<Optional<User>> retrieveOneUser(@PathVariable int id) {
        Optional<User> user = repo.findById(id);                    // Assign found user to a variable, it returns an optional, so that if user is null or not null, a proper object gets returned

        if(!user.isPresent()) // check whether the user is present in the database or not
        {
            throw new UserNotFoundException("id-" + id);    // We pass in a message we want for our exception, it has to be created
        }

        EntityModel<Optional<User>> model = EntityModel.of(user);     // Entity model wraps an object, which is our user and adds links to it

        WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers()); // we specify the retrieveAllUsers method and the framework picks up the URL from GetMapping
        model.add(linkToUsers.withRel("all-users"));

        return model;
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id)        // when we don't return anything we automatically get Http status 200
    {
        repo.deleteById(id);
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {       // adding the valid annotation to tell spring that this RequestBody needs to be validated
        User savedUser = repo.save(user);

        URI location =  ServletUriComponentsBuilder // first we take the uri we currently have and we append the new user id to it, and convert it to an URI
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedUser.getId())
        .toUri();

        return ResponseEntity.created(location).build();    // we are creating a new Response Entity with the created status and pass in the URI we just created, and then we build it ofc
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllPosts(@PathVariable int id) {
        Optional<User> userOptional = repo.findById(id);    // first we need to check whether the user even is present in the database      

        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("id-" + id);    // if not, we throw an exception, but we obviously did try that already
        }

        return userOptional.get().getPosts();
    }
}
