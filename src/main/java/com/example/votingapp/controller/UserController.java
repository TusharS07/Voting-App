package com.example.votingapp.controller;

import com.example.votingapp.Dto.LoginDTO;
import com.example.votingapp.Dto.RegisterDTO;
import com.example.votingapp.Response;
import com.example.votingapp.model.Candidate;
import com.example.votingapp.model.UserModel;
import com.example.votingapp.service.IuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/UserPage")
@CrossOrigin("*")
public class UserController {
    @Autowired
    IuserService iuserService;


    @PostMapping("/Register_User")
    public ResponseEntity<Response> registerUser(@RequestBody RegisterDTO registerDTO) {
        iuserService.RegisterNewUser(registerDTO);
        Response response = new Response(registerDTO, "User Registered Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/Login")
    public ResponseEntity<Response> loginUser(@RequestBody LoginDTO loginDTO) {
       String token = iuserService.login(loginDTO);
        Response response = new Response(token, "Login Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/Logout")
    public ResponseEntity<Response> logoutUser(@RequestParam String token) {
        iuserService.logoutUser(token);
        Response response = new Response("Logout", "Logout Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/Vote")
    public ResponseEntity<Response> voting(@RequestParam String token, @RequestParam int candidateID) {
        iuserService.Vote(token, candidateID);
        Response response = new Response(candidateID, "voting Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/UserData")
    public ResponseEntity<Response> UserData(@RequestParam String token) {
        UserModel userModel = iuserService.fetchUserData(token);
        Response response = new Response(userModel, "UserData");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/Show_All_Candidates")
    public ResponseEntity<Response> getCandidateData() {
        List<Candidate> candidateList = iuserService.showAllCandiates();
        Response response = new Response(candidateList, "We have " + candidateList.size() + " candidates");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
