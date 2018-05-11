package template.costTemplate;

import basicComponents.UITitledBorder;
import template.content.Content;
import template.template.Template;
import template.template.TemplateException;
import template.template.TemplateUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    /* contents' labels */
    private ArrayList<JLabel> labels_content;
    /* estimate cost textfield */
    private ArrayList<JTextField> textFields_estimate;
    /* actual cost textfield */
    private ArrayList<JTextField> textFields_actual;
    /* final size for a textfield */
    private final Dimension dimension_textField = new Dimension(100, 30);
    /* total cost labels */
    private ArrayList<JLabel> labels_total_actual;
    private ArrayList<JLabel> labels_total_estimate;
    /* use to find non-numeric character in text fields*/
    Pattern pattern = Pattern.compile("^[0-9]+$");
    Matcher matcher;
    /* timer used to constantly update UI */
    Timer timer;
    /**/
    private JPanel[] panels_months = new JPanel[12];

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
        panel_textFields.setLayout(new GridLayout(0, 12, 4, 10));
        //panel_textFields.setBorder(UITitledBorder.getTitleBorder("Dự kiến      /      Thực tế"));

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
        int size = template.getNumberOfLeafContents();
        //System.out.println(size);
        if (size == 0) {
            throw new TemplateException("Biểu mẫu rỗng!");
        }
        /* initialize components */
        labels_content = new ArrayList<JLabel>();
        textFields_estimate = new ArrayList<JTextField>();
        textFields_actual = new ArrayList<JTextField>();
        labels_total_actual = new ArrayList<JLabel>();
        labels_total_estimate = new ArrayList<JLabel>();
        for (int i = 0; i < template.getContents().size(); i++) {
            ArrayList<JLabel> contentLabels = generateContentLabels(template.getContents().get(i));
            while (!contentLabels.isEmpty()) {
                JLabel contentLabel = contentLabels.remove(0);
                labels_content.add(contentLabel);
                panel_labels.add(contentLabel);
            }
        }
        for (int k = 0; k < 12; k++) {
            panels_months[k] = new JPanel(new GridLayout(0, 2, 10, 10));
            panels_months[k].setBorder(UITitledBorder.getTitleBorder("Tháng " + (k + 1)));
            for (int i = 0; i < template.getContents().size(); i++) {
                ArrayList<JTextField> costTextFields = generateCostTextFields(template.getContents().get(i));
                while (!costTextFields.isEmpty()) {
                    JTextField estimateTextField = costTextFields.remove(0);
                    JTextField actualTextField = costTextFields.remove(0);
                    textFields_estimate.add(estimateTextField);
                    textFields_actual.add(actualTextField);
                    if (estimateTextField == null) {
                        JLabel label1 = new JLabel("Dự kiến", SwingConstants.CENTER);
                        JLabel label2 = new JLabel("Thực tế", SwingConstants.CENTER);
                        panels_months[k].add(label1);
                        panels_months[k].add(label2);
                    } else {
                        estimateTextField.setPreferredSize(dimension_textField);
                        actualTextField.setPreferredSize(dimension_textField);
                        panels_months[k].add(estimateTextField);
                        panels_months[k].add(actualTextField);
                    }
                }
                //JLabel label_total_estimate = new JLabel("0");
                //JLabel label_total_actual = new JLabel("0");
                //panel_labels.add(new JLabel("Tổng cộng"));
                //labels_total_estimate.add(label_total_estimate);
                //labels_total_actual.add(label_total_actual);
                //panels_months[k].add(label_total_estimate);
                //panels_months[k].add(label_total_actual);
                /* set up components */
            /*labels_content[i] = new JLabel(template.getContents().get(i).getContent());

            textFields_estimate[i] = new JTextField();
            textFields_estimate[i].setPreferredSize(dimension_textField);

            textFields_actual[i] = new JTextField();
            textFields_actual[i].setPreferredSize(dimension_textField);
            /* add components to panels */
            /*panel_labels.add(labels_content[i]);
            panel_textFields.add(textFields_estimate[i]);
            panel_textFields.add(textFields_actual[i]);*/
            }
            panel_textFields.add(panels_months[k]);
        }
        System.out.println(labels_content.size() + " " + textFields_actual.size());
        this.setBorder(UITitledBorder.getTitleBorder("Chi phí năm " + template.getName()));
        Timer timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //detectInvalidInput();
                //updateTotal();
            }
        });
        timer.start();
    }

    /**
     * Generate labels for sub leaf contents of a specific content
     *
     * @param fatherContent the content to set
     * @return generated labels
     */
    private ArrayList<JLabel> generateContentLabels(Content fatherContent) {
        ArrayList<JLabel> contentLabels = new ArrayList<JLabel>();
        JLabel label_content = new JLabel(fatherContent.getContent());
        contentLabels.add(label_content);
        if (!fatherContent.isLeaf()) {
            for (int i = 0; i < fatherContent.getSubContents().size(); i++) {
                ArrayList<JLabel> subContentLabels = generateContentLabels(fatherContent.getSubContents().get(i));
                while (!subContentLabels.isEmpty()) {
                    contentLabels.add(subContentLabels.remove(0));
                }
            }
        }
        return contentLabels;
    }

    /**
     * Generate text fields for sub leaf contents of a specific content
     *
     * @param fatherContent the content to set
     * @return generated text fields
     */
    private ArrayList<JTextField> generateCostTextFields(Content fatherContent) {
        ArrayList<JTextField> costTextFields = new ArrayList<JTextField>();
        if (fatherContent.isLeaf()) {
            costTextFields.add(new JTextField());
            costTextFields.add(new JTextField());
        } else {
            costTextFields.add(null);
            costTextFields.add(null);
            for (int i = 0; i < fatherContent.getSubContents().size(); i++) {
                ArrayList<JTextField> subCostTextFields = generateCostTextFields(fatherContent.getSubContents().get(i));
                while (!subCostTextFields.isEmpty()) {
                    costTextFields.add(subCostTextFields.remove(0));
                }
            }
        }
        return costTextFields;
    }

    /**
     * Get data
     *
     * @return data
     */
    @Override
    public ArrayList<ArrayList<String>> getData() {
        ArrayList<ArrayList<String>> inputs = new ArrayList<ArrayList<String>>();
        System.out.println(textFields_estimate.size());
        for (int i = 0; i < textFields_estimate.size(); i++) {
            ArrayList<String> rowInput = new ArrayList<String>();
            if (textFields_estimate.get(i) != null) {
                //System.out.println(textFields_estimate[i].getText());
                rowInput.add(textFields_estimate.get(i).getText());
                rowInput.add(textFields_actual.get(i).getText());
            } else {
                rowInput.add("");
                rowInput.add("");
            }
            inputs.add(rowInput);
        }
        return inputs;
    }

    /**
     * Clear data
     */
    @Override
    public void clearData() {
        for (int i = 0; i < textFields_estimate.size(); i++) {
            if (textFields_estimate.get(i) != null) {
                textFields_actual.get(i).setText("");
                textFields_estimate.get(i).setText("");
            }
        }
    }

    /**
     * check for non-numeric character
     */
    public void detectInvalidInput() {
        for (int i = 0; i < textFields_estimate.size(); i++) {
            if (textFields_estimate.get(i) == null) continue;
            String text_estimate = textFields_estimate.get(i).getText();
            if (text_estimate.length() > 0) {
                matcher = pattern.matcher(text_estimate);
                if (!matcher.find())
                    textFields_estimate.get(i).setBackground(Color.RED);
                else {
                    if (textFields_estimate.get(i).getBackground().equals(Color.RED))
                        textFields_estimate.get(i).setBackground(Color.WHITE);
                }
            }
            String text_actual = textFields_actual.get(i).getText();
            if (text_actual.length() > 0) {
                matcher = pattern.matcher(text_actual);
                if (!matcher.find())
                    textFields_actual.get(i).setBackground(Color.RED);
                else {
                    if (textFields_actual.get(i).getBackground().equals(Color.RED))
                        textFields_actual.get(i).setBackground(Color.WHITE);
                }
            }
        }
        //panel_inner.updateUI();
    }

    /**
     * calculate total cost of main contents and update labels
     */
    private void updateTotal() {
        Double[] total_actual = new Double[template.getContents().size()];
        Double[] total_estimate = new Double[template.getContents().size()];
        for (int i = 0; i < total_actual.length; i++) {
            total_actual[i] = new Double(0);
            total_estimate[i] = new Double(0);
        }
        int j = -1;
        try {
            for (int i = 0; i < textFields_estimate.size(); i++) {
                //System.out.println("j=" + j);
                if (textFields_estimate.get(i) != null) {
                    if (j != -1) {
                        String actual = textFields_actual.get(i).getText();
                        String estimate = textFields_estimate.get(i).getText();
                        if (actual != null) {
                            if (actual.length() > 0)
                                total_actual[j] += Double.valueOf(actual);
                        }
                        if (estimate != null) {
                            if (estimate.length() > 0)
                                total_estimate[j] += Double.valueOf(estimate);
                        }
                    }
                } else j++;
            }
            for (int i = 0; i < total_estimate.length; i++) {
                //long long_actual = total_actual[i].longValue();
                labels_total_actual.get(i).setText(total_actual[i].toString());
                labels_total_estimate.get(i).setText(total_estimate[i].toString());
            }
        } catch (Exception e) {
            //ignore
            e.printStackTrace();
        }
        panel_inner.updateUI();
    }

    public ArrayList<ArrayList<String>> getData(int month, String contentLayerTwo) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        int size = 0;
        for (int i = 0; i < template.getContents().size(); i++) {
            System.out.println(contentLayerTwo);
            System.out.println(template.getContents().get(i).getContent());
            if (contentLayerTwo.equals(template.getContents().get(i).getContent())) {
                System.out.println("found");
                size = template.getContents().get(i).getSubContents().size();
            }
        }
        int start = textFields_actual.size() / 12 * (month - 1) + 1;
        System.out.println("start = " + start);
        for(int i = 0; i < size; i++) {
            ArrayList<String> row = new ArrayList<>();
            row.add(textFields_estimate.get(start + i).getText());
            row.add(textFields_actual.get(start + i).getText());
            data.add(row);
        }
        //for(int i = 0; i <)
        return data;
    }
}
