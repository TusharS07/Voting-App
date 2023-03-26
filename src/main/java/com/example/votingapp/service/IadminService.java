package com.example.votingapp.service;

import com.example.votingapp.Dto.AddCondidateDTO;
import com.example.votingapp.Dto.LoginDTO;
import com.example.votingapp.Dto.RegisterDTO;
import com.example.votingapp.model.Candidate;
import com.example.votingapp.model.VotingData;

import java.util.List;

public interface IadminService {
    String AddCandidate(String token, AddCondidateDTO addCondidateDTO);

    List<Candidate> showAllCandiates();

    List<VotingData> getVotingRecord( String token, int cadidateID);

    String logoutAdmin(String token);
    String adminLogin(LoginDTO loginDTO);


}
