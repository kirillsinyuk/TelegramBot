package com.bot.service.chart;

import com.bot.model.dto.StatisticDataDto;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public interface PieChartService {
	File create3DPieChart(List<StatisticDataDto> dataset, LocalDate startDate, LocalDate endDate);
}
