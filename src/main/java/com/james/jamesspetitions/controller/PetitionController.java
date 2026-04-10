package com.james.jamesspetitions.controller;

import com.james.jamesspetitions.model.Petition;
import com.james.jamesspetitions.model.Signature;
import com.james.jamesspetitions.service.PetitionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PetitionController {

    private final PetitionService petitionService;

    public PetitionController(PetitionService petitionService) {
        this.petitionService = petitionService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/petitions";
    }

    @GetMapping("/petitions")
    public String viewAllPetitions(Model model) {
        model.addAttribute("petitions", petitionService.getAllPetitions());
        return "petitions";
    }

    @GetMapping("/petitions/create")
    public String showCreateForm(Model model) {
        model.addAttribute("petition", new Petition());
        return "create-petition";
    }

    @PostMapping("/petitions/create")
    public String createPetition(@ModelAttribute Petition petition) {
        petitionService.addPetition(petition);
        return "redirect:/petitions";
    }

    @GetMapping("/petitions/search")
    public String showSearchPage() {
        return "search";
    }

    @GetMapping("/petitions/results")
    public String searchPetitions(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("results", petitionService.searchByTitle(keyword));
        model.addAttribute("keyword", keyword);
        return "search-results";
    }

    @GetMapping("/petitions/{id}")
    public String viewPetition(@PathVariable Long id, Model model) {
        Petition petition = petitionService.getPetitionById(id);
        model.addAttribute("petition", petition);
        model.addAttribute("signature", new Signature());
        return "petition-details";
    }

    @PostMapping("/petitions/{id}/sign")
    public String signPetition(@PathVariable Long id, @ModelAttribute Signature signature) {
        petitionService.addSignature(id, signature);
        return "redirect:/petitions/" + id;
    }
}