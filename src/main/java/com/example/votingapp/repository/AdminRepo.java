package com.example.votingapp.repository;

import com.example.votingapp.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {

    //@Query(value = "SELECT * FROM yogesh_app.admin where username=:username and password=:password", nativeQuery = true)
    Admin findByUsernameAndPassword(String username, String password);

}
