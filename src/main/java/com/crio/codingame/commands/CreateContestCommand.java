package com.crio.codingame.commands;

import java.util.List;

import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.Level;
import com.crio.codingame.exceptions.QuestionNotFoundException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.services.IContestService;

public class CreateContestCommand implements ICommand{

    private final IContestService contestService;

    public CreateContestCommand(IContestService contestService) {
        this.contestService = contestService;
    }

    // TODO: CRIO_TASK_MODULE_CONTROLLER
    // Execute create method of IContestService and print the result.
    // Also Handle Exceptions and print the error messsages if any.
    // Look for the unit tests to see the expected output.
    // Sample Input Token List:- ["CREATE_CONTEST","CRIODO2_CONTEST","LOW Monica","40"]
    // or
    // ["CREATE_CONTEST","CRIODO1_CONTEST","HIGH","Ross"]
    // Hint - Use Parameterized Exceptions in the Service class to match with the Unit Tests Output.

    @Override
    public void execute(List<String> tokens) {
        String contestName = tokens.get(1);
        Level level = Level.valueOf(tokens.get(2)); 
        String contestCreator = tokens.get(3);
        Integer numQuestion;

        if (tokens.size() == 5) {
            numQuestion =  Integer.valueOf(tokens.get(4));
        } else {
            numQuestion =  null;
        }
        try {
            Contest contest = contestService.create(contestName, level, contestCreator, numQuestion);
            System.out.println(contest.toString());
        } catch (UserNotFoundException | QuestionNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
