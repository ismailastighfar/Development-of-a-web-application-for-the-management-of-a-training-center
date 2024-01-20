package com.fst.trainingcenter.web;

import com.fst.trainingcenter.dtos.IndividualDTO;
import com.fst.trainingcenter.exceptions.IndividualAlreadyExistsException;
import com.fst.trainingcenter.exceptions.IndividualNotFoundException;

import com.fst.trainingcenter.services.IndividualService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class IndividualController {
    private IndividualService individualService;

    @GetMapping("/individuals")
    public ResponseEntity<List<IndividualDTO>> getAllIndividuals(){
        return new ResponseEntity<>(
                individualService.getAllIndividuals(),
                HttpStatus.OK
        );
    }

    @GetMapping("/individuals/{id}")
    public ResponseEntity<IndividualDTO> getIndividual(@PathVariable Long id) throws IndividualNotFoundException {
        return new ResponseEntity<>(
                individualService.getIndividual(id),
                HttpStatus.OK
        ) ;
    }

    @PostMapping("/individuals")
    public ResponseEntity<IndividualDTO> saveIndividual(@RequestBody IndividualDTO individualDTO) throws IndividualAlreadyExistsException {
        return new ResponseEntity<>(
                individualService.createIndividual(individualDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/individuals/{id}")
    public ResponseEntity<IndividualDTO> updateIndividual(@PathVariable Long id,@RequestBody IndividualDTO individualDTO) throws IndividualNotFoundException {
        return new ResponseEntity<>(
                individualService.updateIndividual(id,individualDTO),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/individuals/{id}")
    public ResponseEntity<Void> deleteIndividual(@PathVariable Long id) {
        boolean deleted = individualService.deleteIndividual(id);
        if (!deleted)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
