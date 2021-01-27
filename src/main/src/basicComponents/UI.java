package basicComponents;

import basicComponents.chart.BarChart;
import basicComponents.chart.PieChart;
import com.Database.Database;
import org.pushingpixels.substance.api.skin.*;
import template.content.ContentParser;
import template.costTemplate.CostTemplate;
import template.costTemplate.CostTemplateUI;
import template.defaultTemplate.DefaultTemplate;
import template.defaultTemplate.DefaultTemplateUI;
import template.template.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private ArrayList<TemplateUI> templateUIs = new ArrayList<TemplateUI>();
    /* templates list */
    private ArrayList<Template> templates = new ArrayList<Template>();
    /* scroll panes */
    private JScrollPane scrollPane_templateUI;
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
    /**/
    private TemplateUI showingTemplateUI = null;

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
        templateAccessButton.setPreferredSize(new Dimension(200, 30));
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
        templatesAccessPanel.getPanel_inner().add(templateAccessButton);
        templatesAccessPanel.updateUI();
        addTemplateUI(template);
        /* increase key by 1 */
        key++;
    }

    private void addTemplateUI(Template template) {
        TemplateUI templateUI;
        if (template instanceof CostTemplate) {
            templateUI = new CostTemplateUI();
        } else /*if (template instanceof DefaultTemplate)*/ {
            templateUI = new DefaultTemplateUI();
        }
        templateUI.setTemplate(template);
        templateUIs.add(templateUI);
    }

    /**
     * Set up components
     */
    private void setup() {
        /* show the first template in the list for user */
        /*if (!templates.isEmpty()) {
            TemplateUI temp
            templateUI.setTemplate(templates.get(0));
        }*/
        /* set up panels and scroll panes */
        panel_toolbar = new JPanel();
        panel_toolbar.setLayout(new BorderLayout());
        scrollPane_accessPanel = new JScrollPane(templatesAccessPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane_templateUI = new JScrollPane(panel_toolbar, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane_templateUI.getVerticalScrollBar().setUnitIncrement(16);
        //scrollPane_templateUI.setAutoscrolls(false);
        /* set up toolbar buttons */
        button_save = new JButton(new ImageIcon("src\\basicComponents\\ImageIcon\\saveIcon.png"));
        button_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showingTemplateUI == null) {
                    JOptionPane.showMessageDialog(null, "Không có biểu mẫu đang mở");
                    return;
                }
                //System.out.println(templateUI.getData().get(0).get(0));
                Database DB = Database.getInstance();
                DB.ConnectToDatabase();
                try {
                    if(showingTemplateUI instanceof CostTemplateUI) {
                        System.out.println("true");
                        CostTemplateUI cast = (CostTemplateUI) showingTemplateUI;
                        for(int month = 0; month < 12; month++) {
                            for(int i = 0; i < cast.getTemplate().getContents().size(); i++) {
                                ArrayList<ArrayList<String>> data = cast.getData(month, cast.getTemplate().getContents().get(i).getContent());
                                String year = cast.getTemplate().getName().trim();
                                String content = cast.getTemplate().getContents().get(i).getContent();
                                String tableName = year + "#" + String.valueOf(month+1) + "#" +
                                                    DB.getIndexOfContent(year,DB.getParentContent(year, content))+
                                                    "#" + DB.getIndexOfContent(year, content);
                                DB.updateDataOfTable(tableName, data);
                            }
                        }
                    }
                    /*ArrayList<ArrayList<String>> data = showingTemplateUI.getData();
                    System.out.println(data.get(0).get(0));
                    for (int i = 0; i < data.size(); i++) {
                        for (int j = 0; j < data.get(i).size(); j++) {
                            System.out.print(data.get(i).get(j));
                        }
                    }*/
                } catch (IndexOutOfBoundsException ie) {
                    ie.printStackTrace();
                }
                DB.Disconnect();
            }
        });

        button_clear = new JButton(new ImageIcon("src\\basicComponents\\imageIcon\\clearIcon.png"));
        button_clear.setToolTipText("Xóa");
        button_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showingTemplateUI == null) {
                    JOptionPane.showMessageDialog(null, "Không có biểu mẫu đang mở");
                    return;
                }
                showingTemplateUI.clearData();
            }
        });

        button_close = new JButton(new ImageIcon("src\\basicComponents\\imageIcon\\closeIcon.png"));
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

        button_excel = new JButton(new ImageIcon("src\\basicComponents\\imageIcon\\excelIcon.png"));
        button_excel.setToolTipText("Xuất ra Excel(xlsx)");
        button_excel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        button_chart = new JButton(new ImageIcon("src\\basicComponents\\imageIcon\\chartIcon.png"));
        button_chart.setToolTipText("Xuất biểu đồ");
        bar_chart = new JMenuItem("Biểu đồ cột");
        bar_chart.setIcon(new ImageIcon("src\\basicComponents\\imageIcon\\barIcon.png"));
        bar_chart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showingTemplateUI == null) {
                    JOptionPane.showMessageDialog(null, "Không có biểu mẫu đang mở");
                    return;
                }
                if(showingTemplateUI instanceof CostTemplateUI) {
                    CostTemplateUI cast = (CostTemplateUI) showingTemplateUI;
                    BarChart barChart = new BarChart(cast.getTemplate().getName(),"Cả năm","");
                }

            }
        });
        pie_chart = new JMenuItem("Biểu đồ tròn");
        pie_chart.setIcon(new ImageIcon("src\\basicComponents\\imageIcon\\pieIcon.png"));
        pie_chart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showingTemplateUI == null) {
                    JOptionPane.showMessageDialog(null, "Không có biểu mẫu đang mở");
                    return;
                }
                if(showingTemplateUI instanceof CostTemplateUI) {
                    CostTemplateUI cast = (CostTemplateUI) showingTemplateUI;
                    PieChart pieChart = new PieChart(cast.getTemplate().getName(),"Cả năm","");
                }
            }
        });
        button_chart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu popupMenu = new JPopupMenu();
                popupMenu.add(bar_chart);
                popupMenu.add(pie_chart);
                popupMenu.show(button_chart,0, (int) button_chart.getSize().getHeight());
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
        //toolBar.setBackground(Color.ORANGE);
        panel_toolbar.add(toolBar, BorderLayout.NORTH);
        /* add components to main frame */
        this.add(scrollPane_accessPanel, BorderLayout.WEST);
        this.add(scrollPane_templateUI, BorderLayout.CENTER);
    }

    /**
     * Show template which is chosen by user
     *
     * @param key the key of the pressed access button
     * @throws TemplateException
     */
    private void showTemplate(int key) throws TemplateException {
        System.out.println(templateUIs.size());
        if (showingTemplateUI != null) {
            panel_toolbar.remove(showingTemplateUI);
            showingTemplateUI = null;
        }
        if (templateUIs.isEmpty()) {
            return;
        }
        for (int i = 0; i < templateUIs.size(); i++) {
            if (templateUIs.get(i).getTemplate() == null) {
                System.out.println("dick");
            }
            //System.out.println("found");
            if (templateUIs.get(i).getTemplate().getKey() == key) {
                showingTemplateUI = templateUIs.get(i);
                break;
            }
        }
        if (showingTemplateUI == null) {
            throw new TemplateException("Không tìm thấy biểu mẫu!");
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                panel_toolbar.add(showingTemplateUI);
                panel_toolbar.updateUI();
            }
        });
        //panel_toolbar.updateUI();
    }

    /**
     * Close showing template
     */
    private void closeTemplate() throws TemplateException {
        if (showingTemplateUI == null)
            throw new TemplateException("Không có biểu mẫu nào đang mở");
        panel_toolbar.remove(showingTemplateUI);
        panel_toolbar.updateUI();
        showingTemplateUI = null;
    }

    /**
     * test ui
     *
     * @param args
     */
    public static void main(String[] args) {
        //JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(new SubstanceCeruleanLookAndFeel());
                    //UIManager.setLookAndFeel(new SubstanceMarinerLookAndFeel());
                } catch (Exception e) {

                }

                UI ui = new UI();
                CostTemplate a = new CostTemplate("2017");
                a.setContents(ContentParser.parseContents("costcontents.txt"));
                DefaultTemplate b = new DefaultTemplate();
                b.setContents(ContentParser.parseContents("costContents.txt"));
                System.out.println(a.getNumberOfLeafContents());
                ui.addTemplateAccess(a);
                ui.addTemplateAccess(b);
                ui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                //JOptionPane.showMessageDialog(null,  new JTextField());
            }
        });
        /*Timer timer = new Timer(40, new Tick(new JLabel()));
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getClass());
            }
        });
        timer.start();*/
    }
}
