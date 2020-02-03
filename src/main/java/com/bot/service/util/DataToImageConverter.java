package com.bot.service.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DataToImageConverter {

    //временный датасет. Привязать к реальным данным
    private static PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue( "Одежда", new Double(20));
        dataset.setValue( "Еда", new Double(20));
        dataset.setValue( "Подарочки Машеньке", new Double(40));
        dataset.setValue( "Остальное", new Double(10));
        return dataset;
    }

    private static JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Статистика",
                 dataset,
                true,
                true,
                false);

        return chart;
    }

    private static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }

    private static File takePicture(JPanel panel) {
        File image = new File("statistic.jpg");
        panel.setSize(560, 367 );
        BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        panel.print(img.getGraphics());
        try {
            ImageIO.write(img, "jpg", image);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static File convert(){
        return takePicture(createDemoPanel());
    }
}
