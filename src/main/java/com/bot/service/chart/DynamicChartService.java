package com.bot.service.chart;

import com.bot.model.dto.DynamicsDataDto;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface DynamicChartService {
	File createDynamicChart(Map<String, List<DynamicsDataDto>> dataset);
}
