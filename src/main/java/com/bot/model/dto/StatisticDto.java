package com.bot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class StatisticDto {

    private String message;
    private BigDecimal totalSpend;
    private File statisticFile;
    private List<StatisticDataDto> categotySpendings;
}
