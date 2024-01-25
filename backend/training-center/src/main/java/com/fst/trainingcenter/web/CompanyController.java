package com.fst.trainingcenter.web;

import com.fst.trainingcenter.dtos.CompanyDTO;
import com.fst.trainingcenter.dtos.IndividualDTO;
import com.fst.trainingcenter.dtos.TrainingDTO;
import com.fst.trainingcenter.exceptions.CompanyAlreadyExistsException;
import com.fst.trainingcenter.exceptions.CompanyNotFoundException;
import com.fst.trainingcenter.exceptions.IndividualAlreadyExistsException;
import com.fst.trainingcenter.exceptions.IndividualNotFoundException;
import com.fst.trainingcenter.services.CompanyService;
import com.fst.trainingcenter.services.impl.CompanyServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/companies")

public class CompanyController {
    private CompanyService companyService;


    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies(){
        return new ResponseEntity<>(
                companyService.getAllCompanies(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long id) throws CompanyNotFoundException {
        return new ResponseEntity<>(
                companyService.getCompany(id),
                HttpStatus.OK
        ) ;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CompanyDTO>> searchCompanies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<CompanyDTO> result = companyService.searchCompanies(name, email, pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<CompanyDTO> saveCompany(@RequestBody CompanyDTO  companyDTO) throws CompanyAlreadyExistsException {
        return new ResponseEntity<>(
                companyService.createCompany(companyDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id,@RequestBody CompanyDTO companyDTO) throws CompanyNotFoundException {
        return new ResponseEntity<>(
                companyService.updateCompany(id,companyDTO),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) throws CompanyNotFoundException {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
