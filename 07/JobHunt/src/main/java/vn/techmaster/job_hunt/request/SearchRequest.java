package vn.techmaster.job_hunt.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.job_hunt.model.City;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest
{
    private String keyword;
    private City city;
}
