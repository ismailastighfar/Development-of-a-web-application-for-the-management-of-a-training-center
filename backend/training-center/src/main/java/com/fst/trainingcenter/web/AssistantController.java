package com.fst.trainingcenter.web;

import com.fst.trainingcenter.dtos.AssistantDTO;
import com.fst.trainingcenter.exceptions.AssistantAlreadyExistsException;
import com.fst.trainingcenter.exceptions.AssistantNotFoundException;
import com.fst.trainingcenter.services.AssistantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/assistants")
public class AssistantController {

    private AssistantService assistantService;


    @GetMapping
    public ResponseEntity<List<AssistantDTO>> getAllAssistants(){
        return new ResponseEntity<>(
                assistantService.getAllAssistants(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssistantDTO> getAssistant(@PathVariable Long id) throws AssistantNotFoundException {
        return new ResponseEntity<>(
                assistantService.getAssistant(id),
                HttpStatus.OK
        ) ;
    }




    @PostMapping()
    public ResponseEntity<AssistantDTO> saveAssistant(@RequestBody AssistantDTO  assistantDTO) throws AssistantAlreadyExistsException {
        return new ResponseEntity<>(
                assistantService.createAssistant(assistantDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssistantDTO> updateAssistant(@PathVariable Long id,@RequestBody AssistantDTO assistantDTO) throws AssistantNotFoundException {
        return new ResponseEntity<>(
                assistantService.updateAssistant(id,assistantDTO),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssistant(@PathVariable Long id) throws AssistantNotFoundException {
        assistantService.deleteAssistant(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
