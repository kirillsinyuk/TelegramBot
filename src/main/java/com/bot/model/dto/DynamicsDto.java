package com.bot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
public class DynamicsDto {

    private String message;
    private BigDecimal totalSpend;
    private File dynamicsFile;
    private Map<String, List<DynamicsDataDto>> monthSpendings;
}
