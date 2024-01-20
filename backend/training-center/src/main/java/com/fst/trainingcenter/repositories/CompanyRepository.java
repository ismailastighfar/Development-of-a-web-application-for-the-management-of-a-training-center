package com.fst.trainingcenter.repositories;

import com.fst.trainingcenter.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    boolean existsCompanyByName(String name);

}
