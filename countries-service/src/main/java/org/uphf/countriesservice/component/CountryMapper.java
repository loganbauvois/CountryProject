package org.uphf.countriesservice.component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.uphf.countriesservice.dto.CountryDto;
import org.uphf.countriesservice.entities.Country;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CountryMapper {

    public Country toEntity(CountryDto c){
        return Country.builder()
                .nom(c.getNom())
                //.capitale(c.getCapitale())
                .url(c.getUrl())
                .build();
    }

    public CountryDto toDto(Country country) {
        return CountryDto.builder()
                .id(country.getId().toHexString())
                .nom(country.getNom())
                //.capitale(country.getCapitale())
                .url(country.getUrl())
                .build();
    }
}
