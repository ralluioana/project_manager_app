package com.example.cerinte;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskModel, String> {

    List<TaskModel> findAllByEchipa(String echipa);

}
