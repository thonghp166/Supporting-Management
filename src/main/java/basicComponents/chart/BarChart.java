package basicComponents.chart;

import com.Database.Database;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Bar chart in a frame for comparing cost
 *
 * @author Le Trung Thong
 */
public class BarChart extends JFrame {
    /* toolbar of chart frame */
    private ToolChart toolChart;
    /* chart */
    private JFreeChart chart;
    /* panel with chart */
    private ChartPanel chartPanel;

    /**
     * Public constructor
     *
     * @param content  : Content header for chart
     * @param month    : The data come from what month?
     * @param year     : The data come from what year?
     */
    public BarChart (Integer year, Integer month, String content) {
        /*
        Setting default for frame
         */
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(960, 600));
        this.setTitle("Biểu đồ");

        /*
        Create chart in a panel
         */
        chartPanel = new ChartPanel(initChart(year, month,content));
        chartPanel.setPreferredSize(new Dimension(500, 270));
        /**
         * Initialize all components for chart frame
         */
        initComponents(chartPanel);
    }


    /**
     * Initialize chart
     *
     * @param content  : Content header for chart
     * @param month    : The data come from what month?
     * @param year     : The data come from what year?
     * @return a chart
     */
    private JFreeChart initChart( Integer year, Integer month, String content) {
        /*
        Initialize the set of data to make chart
         */
        DefaultCategoryDataset dataset = createDataset(year, month, content);

        chart = ChartFactory.createBarChart("", "",
                "", dataset, PlotOrientation.VERTICAL, true, true, true);
        /*
        Set title of chart
         */
        if (!content.equals(""))
            chart.setTitle("Biểu đồ " + content.toLowerCase() + " tháng " + String.valueOf(month) + " năm " + String.valueOf(year));
        else if (month == -1) {
            chart.setTitle("Biểu đồ " + content.toLowerCase() + " năm " + String.valueOf(year));
        } else chart.setTitle("Biểu đồ chi phí");
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 24));
        chart.getTitle().setPaint(Color.RED);
        /*
         Begin setting up bar chart
         */
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.0);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));

        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER));
        renderer.setBaseItemLabelsVisible(false);
        /*
         * End setting up bar chart
         */


        chart.setBackgroundPaint(Color.white);
        return chart;
    }

    private DefaultCategoryDataset createDataset(Integer year, Integer month, String content) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<String> listContent = new ArrayList<>();
        ArrayList<Double> dukien = new ArrayList<>();
        ArrayList<Double> thucte = new ArrayList<>();

        // Get from database the data in year/month/content
        ArrayList<ArrayList<String>> data = Database.getInstance().getData(year, month, content);

        for (int i = 0; i < data.get(0).size(); i++) {
            listContent.add(data.get(0).get(i));
            dukien.add(Double.valueOf(data.get(1).get(i)));
            thucte.add(Double.valueOf(data.get(2).get(i)));
        }
        // row keys
        String series1 = "Dự kiến";
        String series2 = "Thực tế";

        // column keys
        int i = 0;
        for (String cont : listContent) {
            dataset.addValue(dukien.get(i), series1, cont);
            dataset.addValue(thucte.get(i), series2, cont);
            i++;
        }
        return dataset;
    }

    /**
     * Initialize all components of chart fram
     *
     * @param chartPanel : panel to add chart
     */
    private void initComponents(ChartPanel chartPanel) {
        toolChart = new ToolChart(chartPanel);
        this.setLayout(new BorderLayout());
        this.add(toolChart, BorderLayout.NORTH);
        this.add(chartPanel);
    }

    /**
     * Get tool bar of chart frame
     *
     * @return a tool bar of chart frame
     */
    public ToolChart getToolChart() {
        return toolChart;
    }
}

