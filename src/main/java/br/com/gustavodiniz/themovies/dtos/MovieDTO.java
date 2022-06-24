package br.com.gustavodiniz.themovies.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    private Long id;

    @NotBlank(message = "The overview field is required")
    private String overview;

    @Min(value = 0, message = "The field must be greater than or equal to {value}")
    @Max(value = 10, message = "The field must be less than or equal to {value}")
    @NotNull(message = "The popularity field is required")
    private Double popularity;

    @Length(min = 10, max = 10, message = "The field must have 10 characters")
    @NotBlank(message = "The releaseDate field is required")
    private String releaseDate;

    @NotBlank(message = "The title field is required")
    private String title;

    @Min(value = 0, message = "The field must be greater than or equal to {value}")
    @Max(value = 10, message = "The field must be less than or equal to {value}")
    @NotNull(message = "The voteAverage field is required")
    private Double voteAverage;

    @NotNull(message = "The voteCount field is required")
    private Long voteCount;
}
