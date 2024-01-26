package com.fst.trainingcenter.repositories;

import com.fst.trainingcenter.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompanyRepository extends JpaRepository<Company,Long> , JpaSpecificationExecutor<Company> {

    boolean existsCompanyByName(String name);

}
