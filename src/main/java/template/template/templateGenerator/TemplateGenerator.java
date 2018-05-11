package template.template.templateGenerator;

import template.content.Content;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TemplateGenerator extends JPanel {
    private ArrayList<ContentGenerator> contentGenerators = new ArrayList<ContentGenerator>();
    private JPanel panel_inner;
    public JButton button_add;

    public TemplateGenerator() {
        super(new BorderLayout());
        panel_inner = new JPanel(new GridBagLayout());
        button_add = new JButton("Thêm");
        button_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField textField = new JTextField();
                int confirm = JOptionPane.showConfirmDialog(null, textField, "Nhập nội dung", JOptionPane.INFORMATION_MESSAGE);
                if(confirm == JOptionPane.YES_OPTION) {
                    addContent(textField.getText());
                    update();
                }
            }
        });
        this.add(button_add, BorderLayout.NORTH);
        this.add(panel_inner, BorderLayout.CENTER);
    }

    private void addContent(String name) {
        ContentGenerator contentGenerator = new ContentGenerator(name);
        contentGenerators.add(contentGenerator);
        contentGenerator.setContentNumber(Numbering.getNumber(contentGenerators.size()));
    }
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
                gc.fill = GridBagConstraints.BOTH;
                gc.weightx = 0.5;
                gc.gridx = 0;
                gc.gridy = gridy;
                gc.gridwidth = label_cg.getNumberOfTabs();
                gc.anchor = GridBagConstraints.NORTHWEST;
                gc.insets = new Insets(12, 30 * label_cg.getNumberOfTabs(), 0, 0);
                panel_inner.add(ContentLabel.generateContentLabel(cg), gc);
                for (int j = cg.getSubContentGenerators().size() - 1; j >= 0; j--) {
                    //System.out.println(cg.getSubContentGenerators().get(i).getContentString());
                    stack.add(cg.getSubContentGenerators().get(j));
                }
                gridy += 3;
            }
        }
        panel_inner.revalidate();
        panel_inner.updateUI();
    }
}
