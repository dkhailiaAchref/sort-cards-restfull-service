package com.example.sortCartes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CardsGame {
    private String exerciceId;
    private float dateCreation;
    Candidate CandidateObject;
    Data DataObject;
    private String name;


    // Getter Methods

    public String getExerciceId() {
        return exerciceId;
    }

    public float getDateCreation() {
        return dateCreation;
    }

    public Candidate getCandidate() {
        return CandidateObject;
    }

    public Data getData() {
        return DataObject;
    }

    public String getName() {
        return name;
    }

    // Setter Methods

    public void setExerciceId(String exerciceId) {
        this.exerciceId = exerciceId;
    }

    public void setDateCreation(float dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setCandidate(Candidate candidateObject) {
        this.CandidateObject = candidateObject;
    }

    public void setData(Data dataObject) {
        this.DataObject = dataObject;
    }

    public void setName(String name) {
        this.name = name;
    }
}
