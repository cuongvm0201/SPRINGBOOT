package vn.techmaster.demomanageruser_jobhunt.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.demomanageruser_jobhunt.model.City;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest
{
    private String keyword;
    private City city;
}
