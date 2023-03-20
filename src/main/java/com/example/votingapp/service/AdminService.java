package com.example.votingapp.service;

import com.example.votingapp.Dto.AddCondidateDTO;
import com.example.votingapp.model.Candidate;
import com.example.votingapp.model.VotingData;
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
