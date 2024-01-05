package org.uphf.countriesservice.service;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.uphf.countriesservice.dto.CountryDto;
import org.uphf.countriesservice.entities.Country;
import lombok.RequiredArgsConstructor;
import org.uphf.countriesservice.exception.NoContent;
import org.uphf.countriesservice.component.CountryMapper;
import org.springframework.stereotype.Service;
import org.uphf.countriesservice.repository.CountryRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final RestTemplate restTemplate = new RestTemplate();
    public Country create(CountryDto request) {
        final Country country = countryMapper.toEntity(request);

        return countryRepository.insert(country);
    }

    public List<Country> createAll(List<CountryDto> request) {
        final List<Country> temp = new ArrayList<>();
        request.forEach(x -> temp.add(countryMapper.toEntity(x)));
        return this.countryRepository.insert(temp);
    }

    public Country getCountry() throws NoContent {
        final List<Country> c = countryRepository.findAll();
        if (c.isEmpty()) throw new NoContent();
        Random r = new Random(System.nanoTime());
        final Country temp = c.get(r.nextInt(c.size()));
        temp.setNom(null);
        return temp;
    }

    public Boolean checkResponse(String id, String nom, String username, Integer score) {
        Optional<Country> temp = countryRepository.findById(new ObjectId(id));
        if (temp.isEmpty()){
            return false;
        } else {
            Boolean res = Objects.equals(temp.get().getNom(), nom);
            if (res) {
                try{
                    String url = "http://ms-user:8080/Users/scoreUpdate";
                    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                            .queryParam("username", username)
                            .queryParam("score", score);
                    ResponseEntity<String> x = restTemplate.getForEntity(builder.toUriString(), String.class);
                } catch(Exception e){
                    String url = "http://localhost:8080/Users/scoreUpdate";
                    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                            .queryParam("username", username)
                            .queryParam("score", score);
                    ResponseEntity<String> x = restTemplate.getForEntity(builder.toUriString(), String.class);

                }
            }
            return res;
        }
    }
}
