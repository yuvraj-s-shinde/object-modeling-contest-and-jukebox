package com.crio.codingame.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crio.codingame.entities.User;

public class UserRepository implements IUserRepository{

    private final Map<String,User> userMap;
    private Integer autoIncrement = 0;

    public UserRepository(){
        userMap = new HashMap<String,User>();
    }

    public UserRepository(Map<String, User> userMap) {
        this.userMap = userMap;
        this.autoIncrement = userMap.size();
    }

    @Override
    public User save(User entity) {
        if( entity.getId() == null ){
            autoIncrement++;
            User u = new User(Integer.toString(autoIncrement),entity.getName(),entity.getScore());
            userMap.put(u.getId(),u);
            return u;
        }
        userMap.put(entity.getId(),entity);
        return entity;
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Find all the list of User Present in the Repository
    // Tip:- Use Java Streams

    @Override
    public List<User> findAll() {
        List<User> uList = new ArrayList<>(userMap.values());
        return uList;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        for (Map.Entry<String, User> entry :userMap.entrySet()) {
            if (entry.getValue().getId() == id)
                return true;
        }
        return false;
    }

    @Override
    public void delete(User entity) {
        // TODO Auto-generated method stub
        userMap.values().removeIf(value -> entity.equals(value));
    }
    @Override
    public void delete(Optional<User> entity) {
        // TODO Auto-generated method stub
        userMap.values().removeIf(value -> entity.get().equals(value));
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        userMap.values().removeIf(value -> id == value.getId());
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return (long) userMap.size();
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Find the User Present in the Repository provided name
    // Tip:- Use Java Streams

    @Override
    public Optional<User> findByName(String name) {
        for (Map.Entry<String, User> entry: userMap.entrySet())
        {
            if (entry.getValue().getName().equals(name)) {
                return Optional.ofNullable(entry.getValue());
            }
        }
        return Optional.empty();
    }
    
}
