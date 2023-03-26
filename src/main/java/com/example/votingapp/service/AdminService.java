package com.example.votingapp.service;

import com.example.votingapp.Dto.AddCondidateDTO;
import com.example.votingapp.Dto.LoginDTO;
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
public class AdminService implements IadminService{

    @Autowired
    CandidateRepo candidateRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    VotingDataRepo votingDataRepo;

    @Autowired
    JwtUtils jwtUtils;




    @Override
    public String adminLogin(LoginDTO loginDTO) {
        UserModel admin = userRepo.findByUserNameAndPassword(loginDTO.getUserName(), loginDTO.getPassword());
        if (admin != null && admin.getRole().equals("admin")) {
            String token = jwtUtils.generateToken(loginDTO);
            admin.setLogin(true);
            admin.setId(admin.getId());
            userRepo.save(admin);
            return token;
        }
        throw new VotingAppException("Invaild Admin Credentials");
    }


    @Override
    public String AddCandidate(String token, AddCondidateDTO addCondidateDTO) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel admin = userRepo.findByUserNameAndPassword(loginDTO.getUserName(), loginDTO.getPassword());
        if (admin.isLogin() && admin.getRole().equals("admin")) {
            if (candidateRepo.findByCandidateName(addCondidateDTO.getCandidateName()) == null){
                Candidate candidate = new Candidate(addCondidateDTO.getCandidateName());
                candidateRepo.save(candidate);
                return "Candidate Added successfull";
            }
            throw new VotingAppException("Candidate Already Exist");
        }
        throw new VotingAppException("Invaild Admin Credentials");
    }

    @Override
    public List<Candidate> showAllCandiates() {
        return candidateRepo.findAll();
    }

    @Override
    public List<VotingData> getVotingRecord(String token, int candidateId) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel admin = userRepo.findByUserNameAndPassword(loginDTO.getUserName(), loginDTO.getPassword());
        if (admin.isLogin() && admin.getRole().equals("admin")) {
            List<VotingData> votingData = votingDataRepo.findAllByCandidateId(candidateId);
            return votingData;
        }
        throw new VotingAppException("Invaild Admin Credentials");
    }

    @Override
    public String logoutAdmin(String token) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel admin = userRepo.findByUserNameAndPassword(loginDTO.getUserName(), loginDTO.getPassword());
        if (admin.isLogin() && admin.getRole().equals("admin")) {
            admin.setLogin(false);
            userRepo.save(admin);
            return "Logout Successfully";
        }
        throw new VotingAppException("Invaild Admin Credentials");
    }
}
