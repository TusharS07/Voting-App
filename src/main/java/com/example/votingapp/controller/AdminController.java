package com.example.votingapp.controller;

import com.example.votingapp.Dto.AddCondidateDTO;
import com.example.votingapp.Dto.RegisterDTO;
import com.example.votingapp.Response;
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

    @PostMapping("/Add_Candidate")
    public ResponseEntity<Response> addCandidate(@RequestParam String token, @RequestBody AddCondidateDTO addCondidateDTO) {
        iadminService.AddCandidate(token, addCondidateDTO);
        Response response = new Response(addCondidateDTO, "Candidate Added");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/CandidateVotes")
    public ResponseEntity<Response> getCandidateVotingRecord(@RequestParam String token, @RequestParam int candidateID) {
        List<VotingData> votingDataList = iadminService.getVotingRecord(token, candidateID);
        Response response = new Response(votingDataList, "candidate Votes");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
