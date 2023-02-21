package com.planner.service.chart;

import com.planner.model.dto.*;
import java.io.*;
import java.util.*;

public interface DynamicChartService {
	File createDynamicChart(Map<String, List<DynamicsDataDto>> dataset);
}
