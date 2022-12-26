package com.bot.service.chart.impl;

import com.bot.model.dto.StatisticDataDto;
import com.bot.service.chart.PieChartService;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Service
public class PieChartServiceImpl implements PieChartService {

	public File create3DPieChart(List<StatisticDataDto> dataset, LocalDate startDate, LocalDate endDate) {
		return takePicture(createDemoPanel(dataset, startDate, endDate));
	}

	private File takePicture(JPanel panel) {
		File image = new File("statistic.jpg");
		panel.setSize(640, 480);
		BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
		panel.print(img.getGraphics());

		createJpgImage(image, img);

		return image;
	}

	private JPanel createDemoPanel(List<StatisticDataDto> dataset, LocalDate startDate, LocalDate endDate) {
		JFreeChart chart = createChart(createDataset(dataset), startDate, endDate);
		return new ChartPanel(chart);
	}

	private JFreeChart createChart(PieDataset dataset, LocalDate startDate, LocalDate endDate) {
		String title = getTitleString(startDate, endDate);
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		getAndTunePiePlot(chart);

		return chart;
	}

	private PiePlot3D getAndTunePiePlot(JFreeChart chart) {
		PiePlot3D plot = (PiePlot3D) chart.getPlot();

		plot.setStartAngle(210);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.9f);
		plot.setDepthFactor(0.05f);
		plot.setNoDataMessage("No data to display");
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {1}руб.({2})",
				NumberFormat.getNumberInstance(),
				NumberFormat.getPercentInstance())
		);

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

	private PieDataset createDataset(List<StatisticDataDto> data) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		data.forEach(x ->
				dataset.setValue(x.getCategory().getName(), x.getPrice().doubleValue()));
		return dataset;
	}

	private String getTitleString(LocalDate startDate, LocalDate endDate) {
		return new StringJoiner(" ")
				.add("Статистика c")
				.add(startDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
				.add("по")
				.add(endDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
				.toString();
	}
}
