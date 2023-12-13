package br.com.company.ecommerce.dtos;

import java.time.LocalDate;

import br.com.company.ecommerce.enums.ReportType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Data
public class CreateReportRequest {

    @NotNull(message = "Type must be informed")
    private ReportType type;

    @Past(message = "Start must be a date in the past")
    private LocalDate startAt;

    @PastOrPresent(message = "End must be a date in the past")
    private LocalDate endAt;

    private Long platformId;

}
