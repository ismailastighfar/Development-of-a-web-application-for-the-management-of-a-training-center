package com.fst.trainingcenter.services.impl;

import com.fst.trainingcenter.dtos.CompanyDTO;
import com.fst.trainingcenter.dtos.IndividualDTO;
import com.fst.trainingcenter.entities.Company;
import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.entities.Training;
import com.fst.trainingcenter.exceptions.CompanyAlreadyExistsException;
import com.fst.trainingcenter.exceptions.CompanyNotFoundException;
import com.fst.trainingcenter.exceptions.IndividualAlreadyExistsException;
import com.fst.trainingcenter.exceptions.IndividualNotFoundException;
import com.fst.trainingcenter.mappers.MappersImpl;
import com.fst.trainingcenter.repositories.CompanyRepository;
import com.fst.trainingcenter.security.entities.AppRole;
import com.fst.trainingcenter.security.entities.AppUser;
import com.fst.trainingcenter.services.CompanyService;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public Page<CompanyDTO> searchCompanies(String name, String email, Pageable pageable) {
        Page<Company> CompanyPage = companyRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), "%" + name + "%"));
            }

            if (email != null && !email.isEmpty()) {
                predicates.add(builder.like(root.get("email"), "%" + email + "%"));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        }, pageable);

        return CompanyPage.map(company -> mappers.fromCompany(company));
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

        companyRepository.findById(id).orElseThrow(
                ()->new CompanyNotFoundException("Company Not Found With Id = "+id)
        );

        Company company=mappers.fromCompanyDTO(companyDTO);
        company.setId(id);

        Company companySaved=companyRepository.save(company);
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
