package com.fst.trainingcenter.services.impl;

import com.fst.trainingcenter.dtos.CompanyDTO;
import com.fst.trainingcenter.dtos.IndividualDTO;
import com.fst.trainingcenter.entities.Company;
import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.exceptions.CompanyAlreadyExistsException;
import com.fst.trainingcenter.exceptions.CompanyNotFoundException;
import com.fst.trainingcenter.exceptions.IndividualAlreadyExistsException;
import com.fst.trainingcenter.exceptions.IndividualNotFoundException;
import com.fst.trainingcenter.mappers.MappersImpl;
import com.fst.trainingcenter.repositories.CompanyRepository;
import com.fst.trainingcenter.security.entities.AppRole;
import com.fst.trainingcenter.security.entities.AppUser;
import com.fst.trainingcenter.services.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;
    private MappersImpl mappers;

    @Override
    public List<CompanyDTO> getAllCompanies() {
        List <Company> companies =companyRepository.findAll();


        List<CompanyDTO> companyDTOList=companies.stream().
                map(
                        company -> mappers.fromCompany(company)
                ).toList();
        return companyDTOList;
    }

    @Override
    public CompanyDTO getCompany(Long id) throws CompanyNotFoundException {


        Company company = companyRepository.findById(id).orElseThrow(
                ()->new CompanyNotFoundException("Company Not Found!")
        );

        CompanyDTO companyDto=mappers.fromCompany(company);

        return companyDto;
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) throws CompanyAlreadyExistsException {

        Company company=mappers.fromCompanyDTO(companyDTO);
        if(companyRepository.existsCompanyByName(company.getName()))
            throw new CompanyAlreadyExistsException("Company with name `" + company.getName() + "` already exists!");
        Company savedCompany=companyRepository.save(company);

        return mappers.fromCompany(savedCompany);

    }

    @Override
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) throws CompanyNotFoundException {


        Company company=mappers.fromCompanyDTO(companyDTO);
        Company companyExist = companyRepository.findById(id).orElseThrow(
                ()->new CompanyNotFoundException("Company Not Found With Id = "+id)
        );
        companyExist.setName(company.getName());
        companyExist.setPhone(company.getPhone());
        companyExist.setEmail(company.getEmail());
        companyExist.setAddress(company.getAddress());
        companyExist.setUrl(company.getUrl());
        Company companySaved=companyRepository.save(companyExist);
        return mappers.fromCompany(companySaved);

    }

    @Override
    public boolean deleteCompany(Long id) throws CompanyNotFoundException {

        companyRepository.findById(id).orElseThrow(
                ()->new CompanyNotFoundException("Company Not Found With Id = !"+id)
        );

        companyRepository.deleteById(id);
        return true;
    }
}
