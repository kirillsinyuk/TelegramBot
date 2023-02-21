package com.planner.service.chart;

import com.planner.model.dto.*;
import java.io.*;
import java.time.*;
import java.util.*;

public interface PieChartService {
	File create3DPieChart(List<StatisticDataDto> dataset, LocalDate startDate, LocalDate endDate);
}
