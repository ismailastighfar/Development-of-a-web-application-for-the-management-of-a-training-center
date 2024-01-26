package com.fst.trainingcenter.services;

import com.fst.trainingcenter.dtos.CompanyDTO;
import com.fst.trainingcenter.dtos.TrainingDTO;
import com.fst.trainingcenter.exceptions.CompanyAlreadyExistsException;
import com.fst.trainingcenter.exceptions.CompanyNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {

    List<CompanyDTO> getAllCompanies();
    CompanyDTO getCompany(Long id) throws CompanyNotFoundException;
    Page<CompanyDTO> searchCompanies(String name, String email, Pageable pageable);
    CompanyDTO createCompany(CompanyDTO companyDTO) throws CompanyAlreadyExistsException;
    CompanyDTO updateCompany(Long id,CompanyDTO companyDTO) throws CompanyNotFoundException;
    boolean deleteCompany(Long id) throws CompanyNotFoundException;

}
