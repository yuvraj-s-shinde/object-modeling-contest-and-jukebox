package com.crio.codingame.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;

public class QuestionRepository implements IQuestionRepository {

    private final Map<String,Question> questionMap;
    private Integer autoIncrement = 0;

    public QuestionRepository(){
        questionMap = new HashMap<String,Question>();
    }

    public QuestionRepository(Map<String, Question> questionMap) {
        this.questionMap = questionMap;
        this.autoIncrement = questionMap.size();
    }

    @Override
    public Question save(Question entity) {
        if( entity.getId() == null ){
            autoIncrement++;
            Question q = new Question(Integer.toString(autoIncrement),entity.getTitle(),entity.getLevel(),entity.getScore());
            questionMap.put(q.getId(),q);
            return q;
        }
        questionMap.put(entity.getId(),entity);
        return entity;
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Find all the list of Question Present in the Repository
    // Tip:- Use Java Streams

    @Override
    public List<Question> findAll() {
        List<Question> qList = new ArrayList<>(questionMap.values());
        return qList;
    }

    @Override
    public Optional<Question> findById(String id) {
        return Optional.ofNullable(questionMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        for (Map.Entry<String, Question> entry: questionMap.entrySet()) {
            if (entry.getValue().getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void delete(Question entity) {
        // TODO Auto-generated method stub
        questionMap.values().removeIf(value -> value.equals(entity));
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        questionMap.values().removeIf(value -> value.getId() == id);
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return (long) questionMap.size();
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Find all the list of Question Present in the Repository provided Level
    // Tip:- Use Java Streams

    @Override
    public List<Question> findAllQuestionLevelWise(Level level) {
        List<Question> qList = new ArrayList<>(questionMap.values());
        return qList.stream().filter(q -> q.getLevel().equals(level)).collect(Collectors.toList());
    }
    
}
