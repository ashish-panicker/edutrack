package com.edutrack.student_service.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto {
    private HttpStatus status;
    private Object payload;
}
