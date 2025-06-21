package com.example.demo.dto;

import com.example.demo.common.enums.ProblemType;
import lombok.Data;

@Data
public class MathProblemDTO {
    private Long id;
    private ProblemType type;
    private String question;
    private Double correctAnswer;
    private Long cageId;
}
