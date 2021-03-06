package basicComponents.chart;

import com.Database.Database;
import com.util.util;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.TableOrder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PieChart extends JFrame {
    /* toolbar of chart frame */
    private ToolChart toolChart;
    /* chart */
    private JFreeChart chart;
    /* panel with chart */
    private ChartPanel chartPanel;

    private JPanel panel;

    /**
     * Public constructor
     *  @param year     : The data come from what year?
     * @param month    : The data come from what month?
     * @param content  : Content header for chart
     */
    public PieChart(String year, String month, String content) {
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
        chartPanel = new ChartPanel(initChart(year, month, content));
        chartPanel.setPreferredSize(new Dimension(500, 270));
        /**
         * Initialize all components for chart frame
         */
        initCopmonents(chartPanel);
    }

    /**
     * Initialize chart
     *
     * @param year     : The data come from what year?
     * @param month    : The data come from what month?
     * @param content  : Content header for chart
     * @return a chart
     */
    private JFreeChart initChart(String year, String month, String content) {
        /*
        Initialize the set of data to make chart
         */
        DefaultCategoryDataset dataset = createDataset(year, month, content);
        chart = ChartFactory.createMultiplePieChart("",
                dataset, TableOrder.BY_ROW, true, false, true);

        /*
        Set title of chart
         */
        if (!content.equals(""))
            chart.setTitle("Biểu đồ tỷ lệ " + content.toLowerCase().trim() +
                    " tháng " + String.valueOf(month) + " năm " + String.valueOf(year));
        else if (month.equals("Cả năm"))
            chart.setTitle("Biểu đồ tỷ lệ" + " năm " + String.valueOf(year));
        else chart.setTitle("Biểu đồ tỷ lệ chi phí");
        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 24));
        chart.getTitle().setPaint(Color.RED);

        /*
         Begin setting up bar chart
         */
        final MultiplePiePlot plot = (MultiplePiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        final JFreeChart subchart = plot.getPieChart();

        final PiePlot p = (PiePlot) subchart.getPlot();
        p.setBackgroundPaint(null);
        p.setOutlineStroke(null);
        p.setStartAngle(0);
        p.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));
        p.setMaximumLabelWidth(0.25);
        p.setLabelFont(new Font("SansSerif", Font.BOLD, 12));
        p.setInteriorGap(0.0);

        LegendTitle legendTitle = chart.getLegend();
        legendTitle.setPosition(RectangleEdge.LEFT);
        /*
         * End setting up bar chart
         */
        return chart;

    }

    /**
     * Create data set to create chart
     *
     * @param year     : The data come from what year?
     * @param month    : The data come from what month?
     * @param content  : content needs to create chart
     * @return a data set
     */
    private DefaultCategoryDataset createDataset(String year, String month, String content) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<String> listContent = new ArrayList<>();
        ArrayList<Double> dukien = new ArrayList<>();
        ArrayList<Double> thucte = new ArrayList<>();

        // Get from database the data in year/month/content
        Database DB = Database.getInstance();
        DB.ConnectToDatabase();
        ArrayList<ArrayList<String>> data = DB.getData(year, month, content);
        DB.Disconnect();

        for (int i = 0; i < data.size(); i++) {
            listContent.add(data.get(i).get(0));
            dukien.add(util.stringToDouble(data.get(i).get(1)));
            thucte.add(util.stringToDouble(data.get(i).get(2)));
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
    private void initCopmonents(ChartPanel chartPanel) {
        toolChart = new ToolChart(chartPanel);
        toolChart.getRearrangeBtn().setEnabled(true);
        toolChart.getViewLabelItem().setEnabled(false);
        toolChart.getZoomInBtn().setEnabled(false);
        toolChart.getZoomOutBtn().setEnabled(false);
        this.setLayout(new BorderLayout());
        this.add(toolChart, BorderLayout.NORTH);
//        panel = new JPanel();
        chartPanel.setMinimumSize(this.getSize());
//        panel.add(chartPanel);
////        panel.setLayout(new FlowLayout());
//        this.add(panel);
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