package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.crio.jukebox.entities.User;

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
            User u = new User(Integer.toString(autoIncrement),entity.getName());
            userMap.put(u.getId(),u);
            return u;
        }
        userMap.put(entity.getId(),entity);
        return entity;
    }

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
            if (entry.getValue().getId().equals(id))
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

    @Override
    public User update(User user) {
        userMap.put(user.getId(),user);
        return userMap.get(user.getId());
    }
    
}
