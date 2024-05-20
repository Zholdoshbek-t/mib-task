package kg.tilek.mib.service.mib.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {

    private String fullName;

    private LocalDate date;

    private Integer pageNumber;

    private Integer pageSize;

    private List<Sort.Order> orders;
}
