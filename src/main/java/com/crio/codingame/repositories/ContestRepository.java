package com.crio.codingame.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.Level;

public class ContestRepository implements IContestRepository {

    private final Map<String,Contest> contestMap;
    private Integer autoIncrement = 0;

    
    
    public ContestRepository() {
        contestMap = new HashMap<String,Contest>();
    }

    public ContestRepository(Map<String, Contest> contestMap) {
        this.contestMap = contestMap;
        this.autoIncrement = contestMap.size();
    }

    @Override
    public Contest save(Contest entity) {
        if( entity.getId() == null ){
            autoIncrement++;
            Contest c = new Contest(Integer.toString(autoIncrement),entity.getName(),entity.getQuestions(),entity.getLevel(),entity.getCreator(),entity.getContestStatus());
            contestMap.put(c.getId(),c);
            return c;
        }
        contestMap.put(entity.getId(),entity);
        return entity;
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Find all the list of Contest Present in the Repository
    // Tip:- Use Java Streams

    @Override
    public List<Contest> findAll() {
        List<Contest> cList = new ArrayList<>(contestMap.values());
        return cList;
    }

    @Override
    public Optional<Contest> findById(String id) {
        return Optional.ofNullable(contestMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        for (Map.Entry<String, Contest> entry: contestMap.entrySet()) {
            if (entry.getValue().getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void delete(Contest entity) {
        // TODO Auto-generated method stub
        contestMap.values().removeIf(value -> value.equals(entity));        
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        contestMap.values().removeIf(value -> value.getId() == id);   
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return (long) contestMap.size();
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Find all the list of Contest Present in the Repository provided Level
    // Tip:- Use Java Streams

    @Override
    public List<Contest> findAllContestLevelWise(Level level) {
        List<Contest> cList = new ArrayList<>(contestMap.values());
        return cList.stream().filter(c -> c.getLevel().equals(level)).collect(Collectors.toList());
    }

    
}
