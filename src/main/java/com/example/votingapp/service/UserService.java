package com.example.votingapp.service;

import com.example.votingapp.Dto.LoginDTO;
import com.example.votingapp.Dto.RegisterDTO;
import com.example.votingapp.exception.VotingAppException;
import com.example.votingapp.model.Candidate;
import com.example.votingapp.model.UserModel;
import com.example.votingapp.model.VotingData;
import com.example.votingapp.repository.CandidateRepo;
import com.example.votingapp.repository.UserRepo;
import com.example.votingapp.repository.VotingDataRepo;
import com.example.votingapp.utility.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IuserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    VotingDataRepo votingDataRepo;
    @Autowired
    private CandidateRepo candidateRepo;

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public String RegisterNewUser(RegisterDTO registerDTO) {
        if (userRepo.findByUserName(registerDTO.getUserName()) == null) {
            UserModel registerUser = new UserModel(registerDTO.getUserName(), registerDTO.getEmailId(),registerDTO.getPhoneNo(),registerDTO.getPassword());
            userRepo.save(registerUser);
            return "User Register Successful";
        }
        throw new VotingAppException("User Already Exist");
    }

    @Override
    public String login(LoginDTO loginDTO) {
        UserModel userModel = userRepo.findByUserName(loginDTO.getUserName());
        if (userModel != null) {
            if (userModel.getPassword().equals(loginDTO.getPassword())) {
                String token = jwtUtils.generateToken(loginDTO);
                userModel.setLogin(true);
                userModel.setId(userModel.getId());
                userRepo.save(userModel);
                return token;
            }
            throw new VotingAppException("please check Your Password");
        }
        throw new VotingAppException("Check Your UserName");
    }

    @Override
    public String Vote(String token, int candidateId) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepo.findByUserNameAndPassword(loginDTO.getUserName(), loginDTO.getPassword());
        Candidate candidate = candidateRepo.findById(candidateId).get();
            if (user.isLogin()) {
                if (user.isVotingStatus()) {
                    throw new VotingAppException("Voting is already Completed");
                } else {
                    VotingData votingData = new VotingData(user.getId(), candidateId);
                    candidate.setVoteCount(candidate.getVoteCount() + 1);
                    user.setVotingStatus(true);
                    userRepo.save(user);
                    votingDataRepo.save(votingData);
                    return "Vote Added successfull";
                }
            }
        throw new VotingAppException("Invalid User");
    }

    @Override
    public String logoutUser(String token) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepo.findByUserNameAndPassword(loginDTO.getUserName(), loginDTO.getPassword());
        if (user.isLogin()) {
            user.setLogin(false);
            userRepo.save(user);
            return "Logout Successfully";
        }
        throw new VotingAppException("Already LogOut");
    }

    @Override
    public UserModel fetchUserData(String token) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepo.findByUserNameAndPassword(loginDTO.getUserName(), loginDTO.getPassword());
        if (user.isLogin()) {
            return user;
        }
        throw new VotingAppException("please login first");
    }

    @Override
    public List<Candidate> showAllCandiates() {
        return candidateRepo.findAll();
    }
}
