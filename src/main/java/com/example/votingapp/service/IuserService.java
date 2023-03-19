package com.example.votingapp.service;

import com.example.votingapp.Dto.LoginDTO;
import com.example.votingapp.Dto.RegisterDTO;

public interface IuserService {
    String RegisterNewUser(RegisterDTO registerDTO);
    String login(LoginDTO loginDTO);

    String Vote(String token, int CandidateID);
}
