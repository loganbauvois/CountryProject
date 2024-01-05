package org.uphf.countriesservice.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.uphf.countriesservice.controller.CountryController;
import org.uphf.countriesservice.dto.CountryDto;
import org.uphf.countriesservice.entities.Country;
import org.uphf.countriesservice.repository.CountryRepository;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDB implements ApplicationRunner {
    final private CountryRepository countryRepository;
    final private CountryController countryController;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (countryRepository.findAll().isEmpty()) {
            System.out.println("BDD vide, ajout des données.");
            File file;
            try{
                file = new File("./initdb.json");
                if (!file.exists()) throw new Exception();
            } catch (Exception e){
                file = new File("./src/main/resources/initdb.json");
            }
            ObjectMapper objectMapper = new ObjectMapper();

            List<CountryDto> countries = Arrays.asList(objectMapper.readValue(file, CountryDto[].class));

            countryController.addCountries(countries);
        }
    }

}
