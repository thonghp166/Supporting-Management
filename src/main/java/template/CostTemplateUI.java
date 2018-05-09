package template;

import basicComponents.UITitledBorder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * UI panel for 'Biểu mẫu chi phí'
 *
 * @author Ha Tuan Phong
 */
public class CostTemplateUI extends TemplateUI {
    /* an inside panel with flow layout */
    private JPanel panel_inner;
    /* a panel which contains all the textfields */
    private JPanel panel_textFields;
    /* a panel which contains all the contents' labels */
    private JPanel panel_labels;
    /* 'biểu mẫu chi phí */
    private Template template;

    /* contents' labels */
    private JLabel[] labels_content;
    /* estimate cost textfield */
    private JTextField[] textFields_estimate;
    /* actual cost textfield */
    private JTextField[] textFields_actual;
    /* final size for a textfield */
    private final Dimension dimension_textField = new Dimension(100, 30);

    /**
     * Public constructor
     * Setup panels
     */
    public CostTemplateUI() {
        super();
        this.setLayout(new FlowLayout());
        /* set up panels */
        panel_inner = new JPanel();
        panel_inner.setLayout(new BorderLayout());

        panel_textFields = new JPanel();
        panel_textFields.setLayout(new GridLayout(0, 2, 4, 10));
        panel_textFields.setBorder(UITitledBorder.getTitleBorder("Dự kiến      /      Thực tế"));

        panel_labels = new JPanel();
        panel_labels.setLayout(new GridLayout(0, 1, 4, 10));
        panel_labels.setBorder(UITitledBorder.getTitleBorder("Nội dung"));

        panel_inner.add(panel_labels, BorderLayout.WEST);
        panel_inner.add(panel_textFields, BorderLayout.CENTER);

        this.add(panel_inner);
    }

    /**
     * Set cost template
     *
     * @param template the template to set
     */
    @Override
    public void setTemplate(Template template) {
        this.template = template;
        try {
            setup();
        } catch (TemplateException te) {
            JOptionPane.showMessageDialog(null, te.getMessage());
            return;
        }
    }
    /**
     * setup labels and textfields then add to panels
     */
    private void setup() throws TemplateException {
        /* get template's size */
        int size = template.getContents().size();
        if (size == 0) {
            throw new TemplateException("Biểu mẫu rỗng!");
        }
        /* initialize components */
        labels_content = new JLabel[size];
        textFields_estimate = new JTextField[size];
        textFields_actual = new JTextField[size];
        for (int i = 0; i < size; i++) {
            /* set up components */
            labels_content[i] = new JLabel(template.getContents().get(i).getContent());

            textFields_estimate[i] = new JTextField();
            textFields_estimate[i].setPreferredSize(dimension_textField);

            textFields_actual[i] = new JTextField();
            textFields_actual[i].setPreferredSize(dimension_textField);
            /* add components to panels */
            panel_labels.add(labels_content[i]);
            panel_textFields.add(textFields_estimate[i]);
            panel_textFields.add(textFields_actual[i]);
        }
        this.setBorder(UITitledBorder.getTitleBorder(template.getName()));
    }

    /**
     * Get data
     *
     * @return data
     */
    @Override
    public ArrayList<ArrayList<String>> getData() {
        ArrayList<ArrayList<String>> inputs = new ArrayList<ArrayList<String>>();
        System.out.println(textFields_estimate.length);
        for (int i = 0; i < textFields_estimate.length; i++) {
            ArrayList<String> rowInput = new ArrayList<String>();
            //System.out.println(textFields_estimate[i].getText());
            rowInput.add(textFields_estimate[i].getText());
            rowInput.add(textFields_actual[i].getText());
            inputs.add(rowInput);
        }
        return inputs;
    }

    /**
     * Clear data
     */
    @Override
    public void clearData() {
        for (int i = 0; i < textFields_estimate.length; i++) {
            textFields_actual[i].setText("");
            textFields_estimate[i].setText("");
        }
    }
}
