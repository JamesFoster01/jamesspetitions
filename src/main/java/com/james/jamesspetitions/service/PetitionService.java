package com.james.jamesspetitions.service;

import com.james.jamesspetitions.model.Petition;
import com.james.jamesspetitions.model.Signature;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetitionService {

    private final List<Petition> petitions = new ArrayList<>();
    private Long nextId = 1L;

    public PetitionService() {
        Petition p1 = new Petition(nextId++, "Save Local Bus Routes",
                "Protect local public transport services in our town.",
                "James");
        Petition p2 = new Petition(nextId++, "More Study Spaces",
                "Increase quiet study areas on campus for students.",
                "Amy");

        p1.addSignature(new Signature("Alan", "alan@example.com"));
        p2.addSignature(new Signature("Sarah", "sarah@example.com"));

        petitions.add(p1);
        petitions.add(p2);
    }

    public List<Petition> getAllPetitions() {
        return petitions;
    }

    public void addPetition(Petition petition) {
        petition.setId(nextId++);
        petitions.add(petition);
    }

    public Petition getPetitionById(Long id) {
        return petitions.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Petition> searchByTitle(String keyword) {
        return petitions.stream()
                .filter(p -> p.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    public void addSignature(Long petitionId, Signature signature) {
        Petition petition = getPetitionById(petitionId);
        if (petition != null) {
            petition.addSignature(signature);
        }
    }
}