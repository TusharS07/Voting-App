package com.example.votingapp.service;

import com.example.votingapp.Dto.LoginDTO;
import com.example.votingapp.Dto.RegisterDTO;
import com.example.votingapp.exception.VotingAppException;
import com.example.votingapp.model.UserModel;
import com.example.votingapp.model.VotingData;
import com.example.votingapp.repository.UserRepo;
import com.example.votingapp.repository.VotingDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IuserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    VotingDataRepo votingDataRepo;


    @Override
    public String RegisterNewUser(RegisterDTO registerDTO) {
        if (userRepo.findByEmailId(registerDTO.getEmailId()) == null) {
            UserModel registerUser = new UserModel(registerDTO.getUserName(), registerDTO.getEmailId(),registerDTO.getPhoneNo(),registerDTO.getPassword());
            userRepo.save(registerUser);
            return "User Register Successful";
        }
        throw new VotingAppException("User Already Exist");
    }

    @Override
    public String login(LoginDTO loginDTO) {
        UserModel userModel = userRepo.findByEmailId(loginDTO.getEmailId());
        if (userModel != null) {
            if (userModel.getPassword().equals(loginDTO.getPassword())) {
                userModel.setLogin(true);
                userModel.setId(userModel.getId());
                userRepo.save(userModel);
                return "Login Successfull";
            }
            throw new VotingAppException("please check Your Password");
        }
        throw new VotingAppException("Check Your Email-ID");
    }

    @Override
    public String Vote(int userId, int candidateId) {
        UserModel user = userRepo.findById(userId).get();
            if (user.isLogin()) {
                if (votingDataRepo.findByUserId(user.getId()) == null) {
                    VotingData votingData = new VotingData(user.getId(), candidateId);
                    votingDataRepo.save(votingData);
                    return "Vote Added successfull";
                }
                throw new VotingAppException("Voting is already Completed");
            }
        throw new VotingAppException("Invalid User");
    }
}
