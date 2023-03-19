package com.example.votingapp.service;

import com.example.votingapp.Dto.LoginDTO;
import com.example.votingapp.Dto.RegisterDTO;
import com.example.votingapp.exception.VotingAppException;
import com.example.votingapp.model.UserModel;
import com.example.votingapp.model.VotingData;
import com.example.votingapp.repository.UserRepo;
import com.example.votingapp.repository.VotingDataRepo;
import com.example.votingapp.utility.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IuserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    VotingDataRepo votingDataRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    JwtUtils jwtUtils;

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
                String token = jwtUtils.generateToken(loginDTO);
                userModel.setLogin(true);
                userModel.setId(userModel.getId());
                userRepo.save(userModel);
                return token;
            }
            throw new VotingAppException("please check Your Password");
        }
        throw new VotingAppException("Check Your Email-ID");
    }

    @Override
    public String Vote(String token, int candidateId) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepo.findByEmailIdAndPassword(loginDTO.getEmailId(), loginDTO.getPassword());
            if (user.isLogin()) {
                VotingData votingData = new VotingData(user.getId(), candidateId);
                votingDataRepo.save(votingData);
                return "Vote Added successfull";
            }
        throw new VotingAppException("Invalid User");
    }
}
