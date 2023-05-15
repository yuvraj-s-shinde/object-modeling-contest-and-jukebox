package com.crio.codingame.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.ContestStatus;
import com.crio.codingame.entities.RegisterationStatus;
import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.InvalidOperationException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.repositories.IContestRepository;
import com.crio.codingame.repositories.IUserRepository;

public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IContestRepository contestRepository;

    public UserService(IUserRepository userRepository, IContestRepository contestRepository) {
        this.userRepository = userRepository;
        this.contestRepository = contestRepository;
    }
    // TODO: CRIO_TASK_MODULE_SERVICES
    // Create and store User into the repository.
    @Override
    public User create(String name) {
     User user = new User(name, 1500);
     return userRepository.save(user);
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Get All Users in Ascending Order w.r.t scores if ScoreOrder ASC.
    // Or
    // Get All Users in Descending Order w.r.t scores if ScoreOrder DESC.

    @Override
    public List<User> getAllUserScoreOrderWise(ScoreOrder scoreOrder){
        List<User> uList = userRepository.findAll();
        if(scoreOrder.equals(ScoreOrder.ASC)) {
            Collections.sort(uList, new Comparator<User>() {
                public int compare(User u1, User u2) {
                    return u1.getScore() > u2.getScore() ? 1 : u1.getScore() < u2.getScore() ? -1 : 0;
                }
            });
        } else if (scoreOrder.equals(ScoreOrder.DESC)) {
            Collections.sort(uList, new Comparator<User>() {
                public int compare(User u1, User u2) {
                    return u1.getScore() < u2.getScore() ? 1 : u1.getScore() > u2.getScore() ? -1 : 0;
                }
            });
        } else {
            return null;
        }
        return uList;
    }

    @Override
    public UserRegistrationDto attendContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {
        Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new ContestNotFoundException("Cannot Attend Contest. Contest for given id:"+contestId+" not found!"));
        User user = userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException("Cannot Attend Contest. User for given name:"+ userName+" not found!"));
        if(contest.getContestStatus().equals(ContestStatus.IN_PROGRESS)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is in progress!");
        }
        if(contest.getContestStatus().equals(ContestStatus.ENDED)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is ended!");
        }
        if(user.checkIfContestExists(contest)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is already registered!");
        }
        user.addContest(contest);
        userRepository.save(user);
        return new UserRegistrationDto(contest.getName(), user.getName(),RegisterationStatus.REGISTERED);
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Withdraw the user from the contest
    // Hint :- Refer Unit Testcases withdrawContest method

    @Override
    public UserRegistrationDto withdrawContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {
        // if (!contestRepository.existsById(contestId)) {
        //     throw new ContestNotFoundException("context not found");
        // } else if (userRepository.findByName(userName).isPresent()) {
        //     throw new UserNotFoundException("user not found");
        // } else if (contestRepository.findById(contestId).get().getContestStatus().equals(ContestStatus.IN_PROGRESS) || 
        // contestRepository.findById(contestId).get().getContestStatus().equals(ContestStatus.ENDED)) {
        //     throw new InvalidOperationException("contest either in progress or ended");
        // } else if (contestRepository.findById(contestId).get().getCreator().getName().equals(userName)) {
        //     throw new InvalidOperationException("contest creator cannot withdraw");
        // } else if (userRepository.findByName(userName).get().getContests().stream().filter(c -> c.getId().equals(contestId)).findAny().equals(Optional.empty())) {
        //     throw new InvalidOperationException("user not registered for contest yet");
        // } else {
        //     userRepository.delete(userRepository.findByName(userName).get());
        // }
        // return 


        Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new ContestNotFoundException("Cannot Attend Contest. Contest for given id:"+contestId+" not found!"));
        User user = userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException("Cannot Attend Contest. User for given name:"+ userName+" not found!"));
        if(contest.getContestStatus().equals(ContestStatus.IN_PROGRESS)){
            throw new InvalidOperationException("Cannot Withdraw. Contest for given id:"+contestId+" is in progress!");
        }
        if(contest.getContestStatus().equals(ContestStatus.ENDED)){
            throw new InvalidOperationException("Cannot Withdraw. Contest for given id:"+contestId+" is ended!");
        }
        if(!user.checkIfContestExists(contest)){
            throw new InvalidOperationException("Cannot Withdraw. Contest for given id:"+contestId+" does not exist!");
        }
        if(user.getContests().stream().filter(c -> c.getId().equals(contestId)).findAny() == null){
            throw new InvalidOperationException("Cannot Withdraw. User not registered for Contest for given id:"+contestId+" ");
        }
        if(contest.getCreator().getName().equals(userName)){
            throw new InvalidOperationException("Cannot Withdraw. User: "+userName+" is creator of contest!");
        }
        user.deleteContest(contest);
        userRepository.save(user);
        return new UserRegistrationDto(contest.getName(), user.getName(),RegisterationStatus.NOT_REGISTERED);
    }
}
