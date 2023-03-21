package com.example.votingapp.service;

import com.example.votingapp.Dto.AddCondidateDTO;
import com.example.votingapp.Dto.RegisterDTO;
import com.example.votingapp.exception.VotingAppException;
import com.example.votingapp.model.Admin;
import com.example.votingapp.model.Candidate;
import com.example.votingapp.model.UserModel;
import com.example.votingapp.model.VotingData;
import com.example.votingapp.repository.AdminRepo;
import com.example.votingapp.repository.CandidateRepo;
import com.example.votingapp.repository.UserRepo;
import com.example.votingapp.repository.VotingDataRepo;
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
    AdminRepo adminRepo;


    @Override
    public String RegisterAdmin(String username, String password) {
        Admin admin = new Admin(username, password);
        adminRepo.save(admin);
            return "Admin Register Successful";
    }

    @Override
    public String adminLogin(String username, String password) {
        Admin admin = adminRepo.findByUsernameAndPassword(username, password);
        if (admin != null) {
            admin.setLogin(true);
            admin.setId(admin.getId());
            adminRepo.save(admin);
        }
        throw new VotingAppException("Invaild Admin Credentials");
    }


    @Override
    public String AddCandidate(AddCondidateDTO addCondidateDTO) {
            Candidate candidate = new Candidate(addCondidateDTO.getCandidateName());
            candidateRepo.save(candidate);
            return "Candidate Added successfull";
    }

    @Override
    public List<Candidate> showAllCandiates() {
        return candidateRepo.findAll();
    }

    @Override
    public List<VotingData> getVotingRecord(int candidateId) {
            List<VotingData> votingData = votingDataRepo.findAllByCandidateId(candidateId);
            return votingData;
    }
}
