package com.example.votingapp.controller;

import com.example.votingapp.Dto.AddCondidateDTO;
import com.example.votingapp.Dto.LoginDTO;
import com.example.votingapp.Response;
import com.example.votingapp.model.Candidate;
import com.example.votingapp.model.VotingData;
import com.example.votingapp.service.IadminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/AdminPage")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    IadminService iadminService;



    @PostMapping("/AdminLogin")
    public ResponseEntity<Response> loginAdmin(@RequestBody LoginDTO loginDTO) {
        String token = iadminService.adminLogin(loginDTO);
        Response response = new Response(token, "Login Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/Logout")
    public ResponseEntity<Response> logoutAdmin(@RequestParam String token) {
        iadminService.logoutAdmin(token);
        Response response = new Response("Logout", "Logout Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/Add_Candidate")
    public ResponseEntity<Response> addCandidate(@RequestParam String token, @RequestBody AddCondidateDTO addCondidateDTO) {
        iadminService.AddCandidate(token, addCondidateDTO);
        Response response = new Response(addCondidateDTO, "Candidate Added");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/CandidateVotes")
    public ResponseEntity<Response> getCandidateVotingRecord(@RequestParam String token,@RequestParam int candidateID) {
        List<VotingData> votingDataList = iadminService.getVotingRecord(token, candidateID);
        Response response = new Response(votingDataList, "candidate Have "+votingDataList.size()+" Votes");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/Show_All_Candidates")
    public ResponseEntity<Response> getCandidateData() {
        List<Candidate> candidateList = iadminService.showAllCandiates();
        Response response = new Response(candidateList, "We have " +candidateList.size()+ " candidates");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
