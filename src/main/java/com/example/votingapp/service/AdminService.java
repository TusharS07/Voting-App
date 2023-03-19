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
import org.modelmapper.ModelMapper;
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
    ModelMapper modelMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public String AddCandidate(String token, AddCondidateDTO addCondidateDTO) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepo.findByEmailIdAndPassword(loginDTO.getEmailId(), loginDTO.getPassword());
        if (user.isLogin()) {
            Candidate candidate = new Candidate(addCondidateDTO.getCandidateName());
            candidateRepo.save(candidate);
            return "Candidate Added successfull";
        }
        throw new VotingAppException("Invalid User");
    }

    @Override
    public List<Candidate> showAllCandiates(String token) {
        return null;
    }

    @Override
    public List<VotingData> getVotingRecord(String token, int candidateId) {
        LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepo.findByEmailIdAndPassword(loginDTO.getEmailId(), loginDTO.getPassword());
        if (user.isLogin()) {
            List<VotingData> votingData = votingDataRepo.findAllByCandidateId(candidateId);
            return votingData;
        }
        throw new VotingAppException("Invalid User");
    }
}
