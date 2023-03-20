package com.example.votingapp.service;

import com.example.votingapp.Dto.AddCondidateDTO;
import com.example.votingapp.model.Candidate;
import com.example.votingapp.model.VotingData;

import java.util.List;

public interface IadminService {
    String AddCandidate( AddCondidateDTO addCondidateDTO);

    List<Candidate> showAllCandiates();

    List<VotingData> getVotingRecord( int candidateId);


}
