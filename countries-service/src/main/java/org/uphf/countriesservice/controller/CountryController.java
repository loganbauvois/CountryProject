package org.uphf.countriesservice.controller;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import org.uphf.countriesservice.dto.CountryDto;
import org.uphf.countriesservice.entities.Country;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.uphf.countriesservice.exception.NoContent;
import org.uphf.countriesservice.mapper.CountryMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.uphf.countriesservice.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/countries")
@Slf4j
@RequiredArgsConstructor
@Tag(
    name = "Country controller",
    description = "API Country"
)
public class CountryController {

    private final CountryService countryService;

    private final CountryMapper countryMapper;

    @PostMapping
    @Operation(
        summary = "Create a country",
        description = "Create a country with the provided data.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Response if the country was successfully created",
                content = @Content(mediaType = "application/json", schema = @Schema(allOf = CountryDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Response if the provided data is not valid",
                content = @Content(mediaType = "application/json")
            )
        }
    )
    public ResponseEntity<CountryDto> addCountry(@Parameter(description = "Infos du pays à ajouter") @Valid @RequestBody CountryDto request){
        if (request.getId() == null && /*request.getCapitale() != null &&*/ request.getNom() != null && request.getUrl() != null){
            final Country country = countryService.create(request);
            final CountryDto dto = countryMapper.toDto(country);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/all")
    @Operation(
            summary = "Create countries",
            description = "Create countries with the provided datas.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Response if all the countries were successfully created",
                            content = @Content(mediaType = "application/json", schema = @Schema(allOf = CountryDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Response if the provided data is not valid",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<List<CountryDto>> addCountries(@Parameter(description = "Liste d'Infos des pays à ajouter") @Valid @RequestBody List<CountryDto> request) {
        if (request.stream().allMatch(x -> x.getId() == null /*&& x.getCapitale() != null */&& x.getNom() != null && x.getUrl() != null)) {
            final List<Country> countries = countryService.createAll(request);
            final List<CountryDto> dto = countries.stream().map(countryMapper::toDto).toList();
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/question")
    @Operation(
            summary = "Returns a question",
            description = "Returns a random country without the country name.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Response if the country was successfully sent",
                            content = @Content(mediaType = "application/json", schema = @Schema(allOf = CountryDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Response if there is no country in the database.",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<CountryDto> getQuestion(){
        try {
            final Country c = countryService.getCountry();
            final CountryDto dto = countryMapper.toDto(c);
            return ResponseEntity.ok().body(dto);
        } catch (NoContent e){
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/reponse")
    @Operation(
            summary = "Returns the answer",
            description = "Returns true if the ID matches the country name.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "If the arguments are correct",
                            content = @Content(mediaType = "application/json", schema = @Schema(allOf = CountryDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "If there is an error with the arguments.",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<Boolean> getResponse(@RequestParam(required = true) String id, @RequestParam(required = true) String nom, @RequestParam(required = true) String username, @RequestParam(required = true) Integer score){
        if (!ObjectId.isValid(id)){
            return ResponseEntity.badRequest().build();
        }
        Boolean res = countryService.checkResponse(id, nom, username, score);
        return ResponseEntity.ok(res);
    }
}
