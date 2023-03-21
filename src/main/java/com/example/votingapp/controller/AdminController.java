package com.example.votingapp.controller;

import com.example.votingapp.Dto.AddCondidateDTO;
import com.example.votingapp.Dto.LoginDTO;
import com.example.votingapp.Dto.RegisterDTO;
import com.example.votingapp.Response;
import com.example.votingapp.model.Candidate;
import com.example.votingapp.model.VotingData;
import com.example.votingapp.repository.AdminRepo;
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
    @Autowired
    private AdminRepo adminRepo;


    @PostMapping("/Register_Admin")
    public ResponseEntity<Response> registerAdmin(@RequestParam String username, @RequestParam String password) {
        iadminService.adminLogin(username, password);
        Response response = new Response("Admin Added", "Admin Registered Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/AdminLogin")
    public ResponseEntity<Response> loginAdmin(@RequestParam String username, @RequestParam String password) {
        iadminService.adminLogin(username, password);
        Response response = new Response("Admin Login", "Login Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/Add_Candidate")
    public ResponseEntity<Response> addCandidate(@RequestBody AddCondidateDTO addCondidateDTO) {
        iadminService.AddCandidate(addCondidateDTO);
        Response response = new Response(addCondidateDTO, "Candidate Added");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/CandidateVotes")
    public ResponseEntity<Response> getCandidateVotingRecord(@RequestParam int candidateID) {
        List<VotingData> votingDataList = iadminService.getVotingRecord(candidateID);
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
