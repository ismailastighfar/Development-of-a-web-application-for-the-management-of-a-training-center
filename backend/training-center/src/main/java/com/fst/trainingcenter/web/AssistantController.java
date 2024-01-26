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

public class AssistantController {

    private AssistantService assistantService;


    @GetMapping("/assistants")
    public ResponseEntity<List<AssistantDTO>> getAllAssistants(){
        return new ResponseEntity<>(
                assistantService.getAllAssistants(),
                HttpStatus.OK
        );
    }

    @GetMapping("/assistants/{id}")
    public ResponseEntity<AssistantDTO> getAssistant(@PathVariable Long id) throws AssistantNotFoundException {
        return new ResponseEntity<>(
                assistantService.getAssistant(id),
                HttpStatus.OK
        ) ;
    }




    @PostMapping("/assistants")
    public ResponseEntity<AssistantDTO> saveAssistant(@RequestBody AssistantDTO  assistantDTO) throws AssistantAlreadyExistsException {
        return new ResponseEntity<>(
                assistantService.createAssistant(assistantDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/assistants/{id}")
    public ResponseEntity<AssistantDTO> updateAssistant(@PathVariable Long id,@RequestBody AssistantDTO assistantDTO) throws AssistantNotFoundException {
        return new ResponseEntity<>(
                assistantService.updateAssistant(id,assistantDTO),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/assistants/{id}")
    public ResponseEntity<?> deleteAssistant(@PathVariable Long id) throws AssistantNotFoundException {
        assistantService.deleteAssistant(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/forgot-password/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable("email") String email) {
        assistantService.initiatePasswordReset(email);
        return ResponseEntity.ok("Password reset link sent successfully");
    }

    @GetMapping("/reset-password/{token}/{newPassword}")
    public ResponseEntity<Boolean> resetPassword(@PathVariable("token") String token, @PathVariable("newPassword") String newPassword ) {
        System.out.println("/reset-password/{token}/{newPassword} ");
        return new ResponseEntity<>(
                assistantService.completePasswordReset(token,newPassword),
                HttpStatus.OK);
    }

}
