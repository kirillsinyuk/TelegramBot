package com.bot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jfree.data.time.Month;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DynamicsDataDto {
    private Month month;
    private BigDecimal spend;
    private String userName;

    @Override
    public String toString() {
        return month.toString() + " - " + spend;
    }
}
