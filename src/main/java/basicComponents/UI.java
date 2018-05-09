package basicComponents;

import basicComponents.Chart.BarChart;
import basicComponents.Chart.PieChart;
import com.Database.Database;
import org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel;
import template.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Main UI
 *
 * @author Ha Tuan Phong
 */
public class UI extends JFrame {
    /* toolbar panel */
    private JPanel panel_toolbar;
    /* templates' access panel */
    private TemplatesAccessPanel templatesAccessPanel = new TemplatesAccessPanel();
    /* template panel */
    private TemplateUI templateUI = null;
    /* templates list */
    private ArrayList<Template> templates = new ArrayList<Template>();
    /* scroll panes */
    private JScrollPane scrollPane_toolbar;
    private JScrollPane scrollPane_accessPanel;
    /* template key for access panel, always increase by 1 */
    private int key = 0;
    /* toolbar */
    private JToolBar toolBar;
    /* toolbar buttons */
    private JButton button_save;
    private JButton button_clear;
    private JButton button_close;
    private JButton button_excel;
    private JButton button_chart;
    private JMenuItem bar_chart;
    private JMenuItem pie_chart;

    /**
     * Public constructor
     */
    public UI() {
        super();
        this.setName("Ban hành chính Viettel");
        this.setMinimumSize(new Dimension(600, 400));
        this.setLayout(new BorderLayout());
        this.setJMenuBar(MenuBar.getInstance());
        setup();
        this.setVisible(true);
    }

    /**
     * Add template
     *
     * @param template the template to add
     */
    public void addTemplateAccess(Template template) {
        /* add template to the list */
        template.setKey(key);
        templates.add(template);
        /* Create new access button and add to panel */
        TemplateAccessButton templateAccessButton = templatesAccessPanel.addAccess(template);
        templateAccessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showTemplate(templateAccessButton.getKey());
                } catch (TemplateException te) {
                    JOptionPane.showMessageDialog(null, te.getMessage());
                }
            }
        });
        templatesAccessPanel.add(templateAccessButton);
        System.out.println("updated");
        templatesAccessPanel.updateUI();
        /* increase key by 1 */
        key++;
    }

    /**
     * Set up components
     */
    private void setup() {
        /* show the first template in the list for user */
        if (!templates.isEmpty()) {
            templateUI.setTemplate(templates.get(0));
        }
        /* set up panels and scroll panes */
        panel_toolbar = new JPanel();
        panel_toolbar.setLayout(new BorderLayout());
        scrollPane_accessPanel = new JScrollPane(templatesAccessPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane_toolbar = new JScrollPane(panel_toolbar, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        /* set up toolbar buttons */
        button_save = new JButton(new ImageIcon("src\\main\\java\\basicComponents\\imageIcon\\saveIcon.png"));
        button_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (templateUI == null) {
                    JOptionPane.showMessageDialog(null, "Không có biểu mẫu đang mở");
                    return;
                }
                //System.out.println(templateUI.getData().get(0).get(0));
                try {
                    ArrayList<ArrayList<String>> data = templateUI.getData();
                    /* test get data */
                    System.out.println(data.get(0).get(0));
                    for (int i = 0; i < data.size(); i++) {
                        for (int j = 0; j < data.get(i).size(); j++) {
                            System.out.print(data.get(i).get(j));
                        }
                    }
                } catch (IndexOutOfBoundsException ie) {
                    ie.printStackTrace();
                }
            }
        });

        button_clear = new JButton(new ImageIcon("src\\main\\java\\basicComponents\\imageIcon\\clearIcon.png"));
        button_clear.setToolTipText("Xóa");
        button_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (templateUI == null) {
                    JOptionPane.showMessageDialog(null, "Không có biểu mẫu đang mở");
                    return;
                }
                templateUI.clearData();
            }
        });

        button_close = new JButton(new ImageIcon("src\\main\\java\\basicComponents\\imageIcon\\closeIcon.png"));
        button_close.setToolTipText("Đóng biểu mẫu");
        button_close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    closeTemplate();
                } catch (TemplateException te) {
                    JOptionPane.showMessageDialog(null, te.getMessage());
                }
            }
        });

        button_excel = new JButton(new ImageIcon("src\\main\\java\\basicComponents\\imageIcon\\excelIcon.png"));
        button_excel.setToolTipText("Xuất ra Excel(xlsx)");
        button_excel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        button_chart = new JButton(new ImageIcon("src\\main\\java\\basicComponents\\imageIcon\\chartIcon.png"));
        button_chart.setToolTipText("Xuất biểu đồ");
        bar_chart = new JMenuItem("Biểu đồ cột");
        bar_chart.setIcon(new ImageIcon("src\\main\\java\\basicComponents\\imageIcon\\barIcon.png"));
        bar_chart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarChart barChart = new BarChart(0,0,"");
            }
        });
        pie_chart = new JMenuItem("Biểu đồ tròn");
        pie_chart.setIcon(new ImageIcon("src\\main\\java\\basicComponents\\imageIcon\\pieIcon.png"));
        pie_chart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PieChart pieChart = new PieChart(0,0,"");
            }
        });
        button_chart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu popupMenu = new JPopupMenu();
                popupMenu.add(bar_chart);
                popupMenu.add(pie_chart);
                popupMenu.show(button_chart, 0, (int) button_chart.getSize().getHeight());
            }
        });

        /* set up toolbar */
        toolBar = new JToolBar();
        toolBar.setBorder(UITitledBorder.getTitleBorder("Thanh tác vụ"));
        toolBar.add(button_save);
        toolBar.addSeparator();
        toolBar.add(button_clear);
        toolBar.addSeparator();
        toolBar.add(button_close);
        toolBar.addSeparator();
        toolBar.add(button_excel);
        toolBar.addSeparator();
        toolBar.add(button_chart);
        toolBar.addSeparator();
        panel_toolbar.add(toolBar, BorderLayout.NORTH);
        /* add components to main frame */
        this.add(scrollPane_accessPanel, BorderLayout.WEST);
        this.add(scrollPane_toolbar, BorderLayout.CENTER);
    }


    /**
     * Show template which is chosen by user
     *
     * @param key the key of the pressed access button
     * @throws TemplateException
     */
    private void showTemplate(int key) throws TemplateException {
        if (templateUI != null) {
            panel_toolbar.remove(templateUI);
            templateUI = null;
        }
        Template template = null;
        for (int i = 0; i < templates.size(); i++) {
            //System.out.println(templates.get(i).getKey());
            if (templates.get(i).getKey() == key)
                template = templates.get(i);
        }
        if (template == null) {
            throw new TemplateException("Không tìm thấy biểu mẫu!");
        }
        //System.out.println(template.getName());
        if (template.getClass().equals(CostTemplate.class)) {
            //System.out.println("found");
            templateUI = new CostTemplateUI();
            templateUI.setTemplate(template);
        }
        panel_toolbar.add(templateUI);
        panel_toolbar.updateUI();
    }

    /**
     * Close showing template
     */
    private void closeTemplate() throws TemplateException {
        if (templateUI == null)
            throw new TemplateException("Không có biểu mẫu nào đang mở");
        panel_toolbar.remove(templateUI);
        panel_toolbar.updateUI();
        templateUI = null;
    }

    /**
     * test ui
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {

//                    UIManager.setLookAndFeel(new SubstanceGeminiLookAndFeel());
//                    UIManager.setLookAndFeel(new SubstanceDustCoffeeLookAndFeel());
                    UIManager.setLookAndFeel(new SubstanceAutumnLookAndFeel());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                UI ui = new UI();

                CostTemplate a = new CostTemplate("Chi phí 2017");
                a.setContents();
                ui.addTemplateAccess(a);
                ui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
    }
}
