package org.uphf.countriesservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto implements Serializable {
    @Schema(
        description = "ID Country",
        example = "153230865742666bbb666221"
    )
    private String id;
    @Schema(
        description = "Nom du pays",
            example = "Zambie"
    )
    private String nom;
/*    @Schema(
        description = "Capitale du pays",
        example = "Lusaka"
    )
    private String capitale;*/
    @Schema(
        description = "URL de l'image de la silhouette du pays",
        example = "https://silhouettegarden.com/files/images//zambia-silhouette-thumbnail.png"
    )
    private String url;
}
