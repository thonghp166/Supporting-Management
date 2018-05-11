package template.template.templateGenerator;

import template.content.Content;
import template.costTemplate.CostTemplate;
import template.defaultTemplate.DefaultTemplate;
import template.template.Template;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Timer;

public class TemplateGenerator extends JPanel {
    private ArrayList<ContentGenerator> contentGenerators = new ArrayList<ContentGenerator>();
    private JPanel panel_outer;
    private JPanel panel_inner;
    private JScrollPane scrollPane;
    private JToolBar toolBar;
    private JCheckBox isCostTemplate;
    private JTextField textField_templateName;
    private JButton button_save;

    /**
     * Public constructor
     */
    public TemplateGenerator() {
        super(new BorderLayout());
        panel_outer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_inner = new JPanel(new GridBagLayout());
        panel_outer.setBackground(Color.GRAY);
        panel_inner.setBackground(Color.GRAY);
        /*button_add = new JButton("Thêm");
        button_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField textField = new JTextField();
                int confirm = JOptionPane.showConfirmDialog(null, textField, "Nhập nội dung", JOptionPane.INFORMATION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    addContent(textField.getText(), null);
                    update();
                }
            }
        });
        this.add(button_add, BorderLayout.NORTH);*/
        panel_outer.add(panel_inner);
        scrollPane = new JScrollPane(panel_outer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        toolBar = new JToolBar();
        isCostTemplate = new JCheckBox("Biểu mẫu chi phí");
        textField_templateName = new JTextField();
        textField_templateName.setToolTipText("Nếu là biểu mẫu chi phí chỉ nhập năm");
        button_save = new JButton(new ImageIcon("src\\basicComponents\\ImageIcon\\saveIcon.png"));
        button_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTemplate();
            }
        });
        toolBar.add(new JLabel("Tên biểu mẫu: "));
        toolBar.add(textField_templateName);
        toolBar.addSeparator();
        toolBar.add(isCostTemplate);
        toolBar.addSeparator();
        toolBar.add(button_save);
        this.add(toolBar, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        addContent("CHI PHÍ TÒA NHÀ", null);
        update();
    }

    /**
     * Add new content to panel
     *
     * @param name     the content name to set
     * @param cgParent the content parent
     */
    private void addContent(String name, ContentGenerator cgParent) {
        ContentGenerator contentGenerator = new ContentGenerator(name);
        if (cgParent != null) {
            ContentGenerator root = cgParent;
            while (root.getParent() != null) {
                root = root.getParent();
            }
            cgParent.addSubContentGenerator(contentGenerator);
            contentGenerator.setParent(cgParent);
            contentGenerator.setContentNumber(Numbering.getNumber(root, contentGenerator));

        } else {
            contentGenerators.add(contentGenerator);
            contentGenerator.setContentNumber(Numbering.getNumber(contentGenerators.size()));
        }
        System.out.println(contentGenerator.getContentNumber() + contentGenerator.getContentString());
    }

    /**
     * Update panel
     */
    private void update() {
        panel_inner.removeAll();
        ArrayList<ContentGenerator> stack = new ArrayList<ContentGenerator>();
        int gridy = 0;
        for (int i = 0; i < contentGenerators.size(); i++) {
            stack.add(contentGenerators.get(i));
            while (!stack.isEmpty()) {
                ContentGenerator cg = stack.remove(stack.size() - 1);
                System.out.println(cg.getContentString());
                ContentLabel label_cg = ContentLabel.generateContentLabel(cg);
                System.out.println(label_cg.getNumberOfTabs());
                GridBagConstraints gc = new GridBagConstraints();
                gc.fill = GridBagConstraints.NONE;
                gc.weightx = 0.5;
                gc.gridx = 0;
                gc.gridy = gridy;
                //gc.gridwidth = label_cg.getNumberOfTabs();
                gc.anchor = GridBagConstraints.NORTHWEST;
                gc.insets = new Insets(12, 20 * label_cg.getNumberOfTabs(), 0, 0);
                panel_inner.add(ContentLabel.generateContentLabel(cg), gc);
                gc.gridx = 1;
                gc.insets = new Insets(11, 10, 0, 0);
                //gc.gridwidth = 0;
                //gc.insets = new Insets(0, 30 * label_cg.getNumberOfTabs(), 0, 0);
                if (label_cg.getNumberOfTabs() < 2) {
                    JButton button = new JButton(new ImageIcon("src\\basicComponents\\imageIcon\\addIcon.png"));
                    button.setBackground(Color.GRAY);
                    //button.setBorderPainted(false);
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JTextField textField = new JTextField();
                            int confirm = JOptionPane.showConfirmDialog(panel_outer, textField, "Nhập nội dung", JOptionPane.INFORMATION_MESSAGE);
                            if (confirm == JOptionPane.YES_OPTION) {
                                addContent(textField.getText(), cg);
                                update();
                            }
                        }
                    });
                    panel_inner.add(button, gc);
                }
                for (int j = cg.getSubContentGenerators().size() - 1; j >= 0; j--) {
                    //System.out.println(cg.getSubContentGenerators().get(i).getContentString());
                    stack.add(cg.getSubContentGenerators().get(j));
                }
                gridy += 1;
            }
        }
        panel_inner.revalidate();
        panel_inner.updateUI();
    }

    /**
     * Save template to database
     *
     * @return template
     */
    public Template saveTemplate() {
        Template template;
        if (isCostTemplate.isSelected()) {
            template = new CostTemplate(textField_templateName.getText());
        } else {
            template = new DefaultTemplate();
            template.setName(textField_templateName.getText());
        }
        for (ContentGenerator cg : contentGenerators.get(0).getSubContentGenerators()) {
            Content content = new Content(cg.getContentNumber() + cg.getContentString());
            for (ContentGenerator subCg : cg.getSubContentGenerators()) {
                content.addSubContent(new Content(subCg.getContentNumber() + subCg.getContentString()));
            }
        }
        System.out.println(template.getName());
        return template;
    }
}
