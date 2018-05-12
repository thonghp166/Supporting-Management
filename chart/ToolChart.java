package basicComponents.chart;

import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ToolChart extends JToolBar {
    private JButton saveAsPNGBtn;
    private JButton zoomOutBtn;
    private JButton zoomInBtn;
    private JButton rearrangeBtn;
    private JButton printBtn;
    private JCheckBox viewLabelItem;
    private JButton optBtn;
    private ChartPanel chart;

    public ToolChart(ChartPanel chartPanel){
        setFloatable(false);
        setRollover(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setRequestFocusEnabled(false);
        setVisible(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(400,40));
        initComponent(chartPanel);
    }

    private void initComponent(ChartPanel chartPanel) {
        chart = chartPanel;
        // Cac thanh phan chinh cua tool chart
        saveAsPNGBtn = new JButton();
        zoomInBtn = new JButton();
        zoomOutBtn = new JButton();
        rearrangeBtn = new JButton();
        printBtn = new JButton();
        viewLabelItem = new JCheckBox("Hiển thị giá trị");
        optBtn = new JButton("Tuỳ chọn");

        //{
        ImageIcon save = new ImageIcon("src\\main\\java\\basicComponents\\imageIcon\\saveIcon.png");
        saveAsPNGBtn.setIcon(save);
        saveAsPNGBtn.setText("Lưu Ảnh");
        saveAsPNGBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveAsPNGBtn.setFocusable(true);
        saveAsPNGBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        saveAsPNGBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        saveAsPNGBtn.setMnemonic(KeyEvent.VK_S);
        saveAsPNGBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    savePngForm(e);
                } catch (IOException er){
                    er.printStackTrace();
                }
            }
        });
        add(saveAsPNGBtn);
        addSeparator();

        ImageIcon zOut = new ImageIcon("src\\main\\java\\basicComponents\\imageIcon\\zoomOutIcon.png");
        zoomOutBtn.setIcon(zOut);
        zoomOutBtn.setText("Thu nhỏ");
        zoomOutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        zoomOutBtn.setFocusable(true);
        zoomOutBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        zoomOutBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        zoomOutBtn.setMnemonic(KeyEvent.VK_SUBTRACT);
        zoomOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomOutPerform(e);
            }
        });
        add(zoomOutBtn);
        addSeparator();

        ImageIcon zIn = new ImageIcon("src\\main\\java\\basicComponents\\imageIcon\\zoomInIcon.png");
        zoomInBtn.setIcon(zIn);
        zoomInBtn.setText("Phóng to");
        zoomInBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        zoomInBtn.setFocusable(true);
        zoomInBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        zoomInBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        zoomInBtn.setMnemonic(KeyEvent.VK_PLUS);
        zoomInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomInPerform(e);
            }
        });
        add(zoomInBtn);
        addSeparator();

        ImageIcon arr = new ImageIcon("src\\main\\java\\basicComponents\\imageIcon\\arrangeIcon.png");
        rearrangeBtn.setIcon(arr);
        rearrangeBtn.setText("Xếp lại");
        rearrangeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rearrangeBtn.setFocusable(true);
        rearrangeBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        rearrangeBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        rearrangeBtn.setMnemonic(KeyEvent.VK_R);
        rearrangeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rearrangePerform(e);
            }
        });
        add(rearrangeBtn);
        addSeparator();

        ImageIcon print = new ImageIcon("src\\main\\java\\basicComponents\\imageIcon\\printIcon.png");
        printBtn.setIcon(print);
        printBtn.setText("In ra");
        printBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        printBtn.setFocusable(true);
        printBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        printBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        printBtn.setMnemonic(KeyEvent.VK_P);
        printBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printPerform(e);
            }
        });
        add(printBtn);
        addSeparator();

        ImageIcon option = new ImageIcon("src\\main\\java\\basicComponents\\imageIcon\\toolIcon.png");
        optBtn.setIcon(option);
        optBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        optBtn.setFocusable(true);
        optBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        optBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        optBtn.setMnemonic(KeyEvent.VK_V);
        optBtn.setToolTipText("Ctrl V");
        optBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionChart optionChart = new OptionChart(chartPanel);
            }
        });

        add(optBtn);
        addSeparator();

        viewLabelItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPerform(e);
            }
        });
        add(viewLabelItem);


    }

    private void viewPerform(ActionEvent e) {
        if (viewLabelItem.isSelected()){
            chart.getChart().getCategoryPlot().getRenderer().setBaseItemLabelsVisible(true);
        } else {
            chart.getChart().getCategoryPlot().getRenderer().setBaseItemLabelsVisible(false);
        }
    }

    private void printPerform(ActionEvent e) {
        chart.createChartPrintJob();
    }

    private void rearrangePerform(ActionEvent e) {
        chart.restoreAutoRangeBounds();
    }

    private void zoomInPerform(ActionEvent e) {
        chart.zoomInBoth(0.5d,0.5d);
    }

    private void zoomOutPerform(ActionEvent e) {
        chart.zoomOutBoth(-0.5d,-0.5d);
    }

    private void savePngForm(ActionEvent e) throws IOException{
        chart.doSaveAs();
    }
    public JButton getZoomOutBtn() {
        return zoomOutBtn;
    }

    public JButton getZoomInBtn() {
        return zoomInBtn;
    }

    public JButton getRearrangeBtn() {
        return rearrangeBtn;
    }

    public JCheckBox getViewLabelItem() {
        return viewLabelItem;
    }
}
