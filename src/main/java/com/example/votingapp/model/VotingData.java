package com.example.votingapp.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class VotingData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne()
    @JoinColumn(name = "userID")
    private UserModel user;

    @ManyToOne()
    @JoinColumn(name = "candidateId")
    private Candidate candidate;

    public VotingData(UserModel user, Candidate candidate) {
        this.user = user;
        this.candidate = candidate;
    }
}
