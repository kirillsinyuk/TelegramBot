package com.bot.service;

import com.bot.model.dto.StatisticDto;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class DataToImageService {

    @Autowired
    private Logger LOG;

    private PieDataset createDataset(List<StatisticDto> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        data.forEach(x -> dataset.setValue(x.getCategory(), x.getPrice().doubleValue()));
        return dataset;
    }

    private JFreeChart createChart(PieDataset dataset, LocalDate startDate, LocalDate endDate) {
        String title = new StringJoiner(" ")
                .add("Статистика c")
                .add(startDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .add("по")
                .add(endDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .toString();
        final JFreeChart chart = ChartFactory.createPieChart3D(
                title,
                 dataset,
                true,
                true,
                false);

        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(210);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.9f);
        plot.setDepthFactor(0.05f);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0} {1}руб.({2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
        plot.setNoDataMessage("No data to display");

        return chart;
    }

    private JPanel createDemoPanel(List<StatisticDto> dataset, LocalDate startDate, LocalDate endDate) {
        JFreeChart chart = createChart(createDataset(dataset), startDate, endDate);
        return new ChartPanel(chart);
    }

    private File takePicture(JPanel panel) {
        File image = new File("statistic.jpg");
        panel.setSize(560, 367);
        BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        panel.print(img.getGraphics());
        try {
            ImageIO.write(img, "jpg", image);
        }
        catch (IOException e) {
            e.printStackTrace();
            LOG.error("Error while create image", e);
        }
        return image;
    }

    public File convert(List<StatisticDto> dataset, LocalDate startDate, LocalDate endDate){
        return takePicture(createDemoPanel(dataset, startDate, endDate));
    }
}
