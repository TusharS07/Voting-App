package com.example.votingapp.repository;

import com.example.votingapp.model.VotingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotingDataRepo extends JpaRepository<VotingData, Integer> {

    @Query(value = "SELECT * FROM voting_app.voting_data where candidate_id = :candidateID", nativeQuery = true)
    List<VotingData> findByCandidate(int candidateID);

    VotingData findByUser(int userId);
}
