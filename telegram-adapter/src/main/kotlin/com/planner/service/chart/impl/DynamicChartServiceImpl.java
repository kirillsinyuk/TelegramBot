package com.planner.service.chart.impl;

import com.planner.model.dto.*;
import com.planner.service.chart.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.text.*;
import java.util.List;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import lombok.extern.slf4j.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.time.*;
import org.jfree.data.xy.*;
import org.jfree.ui.*;
import org.springframework.stereotype.*;

@Slf4j
@Service
public class DynamicChartServiceImpl implements DynamicChartService {

	public File createDynamicChart(Map<String, List<DynamicsDataDto>> dataset) {
		return takePicture(createDemoPanel(dataset));
	}

	private File takePicture(JPanel panel) {
		File image = new File("dynamics.jpg");
		panel.setSize(640, 500);
		BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
		panel.print(img.getGraphics());

		createJpgImage(image, img);

		return image;
	}

	private JPanel createDemoPanel(Map<String, List<DynamicsDataDto>> dataset) {
		JFreeChart chart = createChart(createDataset(dataset));
		return new ChartPanel(chart);
	}

	private JFreeChart createChart(XYDataset dataset) {
		String title = getTitleString();
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, "Time", "Spended", dataset, true, false, false);

		getAndTunePiePlot(chart);

		return chart;
	}

	private XYPlot getAndTunePiePlot(JFreeChart chart) {
		chart.setBackgroundPaint(Color.WHITE);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.LIGHT_GRAY);
		plot.setDomainGridlinePaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.WHITE);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

		return plot;
	}

	private void createJpgImage(File image, BufferedImage img) {
		try {
			ImageIO.write(img, "jpg", image);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Error while create image", e);
		}
	}

	private XYDataset createDataset(Map<String, List<DynamicsDataDto>> data) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();

		data.forEach((key, value) -> {
			TimeSeries ts = new TimeSeries(key);
			value.forEach(y -> ts.add(y.getMonth(), y.getSpend()));
			dataset.addSeries(ts);
		});
		return dataset;
	}

	private String getTitleString() {
		return "Динамика трат";
	}
}
