package com.example.votingapp.controller;

import com.example.votingapp.Dto.LoginDTO;
import com.example.votingapp.Dto.RegisterDTO;
import com.example.votingapp.Response;
import com.example.votingapp.service.IuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/Vote")
    public ResponseEntity<Response> voting(@RequestParam String token, @RequestParam int candidateID) {
        iuserService.Vote(token, candidateID);
        Response response = new Response(candidateID, "voting Successfull for");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
