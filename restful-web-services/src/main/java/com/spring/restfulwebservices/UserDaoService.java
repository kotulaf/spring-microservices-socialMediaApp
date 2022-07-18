package com.spring.restfulwebservices;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int userCount = 3;


    static {
        users.add(new User(1, "john", new Date()));
        users.add(new User(2, "adam", new Date()));
        users.add(new User(3, "steve", new Date()));
    }

    public List<User> findAll() {
        return users;                   // we don't have to do anything here obviously...
    }

    public User save(User user)
    {
        if(user.getId()==0)             // int cannot be compared to null, therefore we must compare it to zero if we like
        {
            user.setId(++userCount);
        }
        users.add(user); 
        return user;
    }

    public User findOne(int id)         // iterating through our arraylist and looking for the id we need, if its not found, we simply return null
    {
        for(User user:users) {
            if(user.getId() == id)
            {
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id)         // we can't use for to iterate and delete from an arraylist, so we use an iterator for that
    {
        Iterator<User> iterator = users.iterator();     // we specify that the iterator should iterate through our user arraylist

        while(iterator.hasNext())           // while there is a next one in the arraylist, the cycle will be executed
        {
            User user = iterator.next();        // we assign the value or the next object to a user
            if(user.getId() == id)              // check for the id and remove them
            {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
