package com.fst.trainingcenter.services;

import com.fst.trainingcenter.dtos.CompanyDTO;
import com.fst.trainingcenter.exceptions.CompanyAlreadyExistsException;
import com.fst.trainingcenter.exceptions.CompanyNotFoundException;

import java.util.List;

public interface CompanyService {

    List<CompanyDTO> getAllCompanies();
    CompanyDTO getCompany(Long id) throws CompanyNotFoundException;
    CompanyDTO createCompany(CompanyDTO companyDTO) throws CompanyAlreadyExistsException;
    CompanyDTO updateCompany(Long id,CompanyDTO companyDTO) throws CompanyNotFoundException;
    boolean deleteCompany(Long id) throws CompanyNotFoundException;

}
