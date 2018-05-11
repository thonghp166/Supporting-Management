package basicComponents.chart;

import com.Database.Database;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Option Frame to make choice to create chart
 *
 * @author Le Trung Thong
 */

public class OptionChart extends JFrame {

    public OptionChart(ChartPanel chartPanel) {
        initComponents(chartPanel);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }


    private void initComponents(ChartPanel chartPanel) {
        GridBagConstraints gridBagConstraints;

        panel = new JPanel();
        typeBox = new JComboBox<>();
        yearBox = new JComboBox<>();
        monthBox = new JComboBox<>();
        typeLabel = new JLabel();
        yearLabel = new JLabel();
        monthLabel = new JLabel();
        createBtn = new JButton();
        cancelBtn = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tùy chọn biểu đồ");

        panel.setBackground(new Color(204, 204, 204));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel.setForeground(new Color(102, 0, 102));
        panel.setPreferredSize(new Dimension(300, 300));
        panel.setLayout(new GridBagLayout());


        // Return a list of content lv 1 and 2
        ArrayList<String> listType = Database.getInstance().getListContents();
        String[] listCont = new String[listType.size()];
        int i = 0;
        for (String cont : listType) {
            if (!cont.equals(cont.toUpperCase()))
                cont = "    " + cont;
            listCont[i] = cont;
            System.out.println(listCont[i]);
            i++;
        }
        typeBox.setModel(new DefaultComboBoxModel<>(listCont));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(85, 19, 0, 147);
        panel.add(typeBox, gridBagConstraints);

        yearBox.setModel(new DefaultComboBoxModel<>(new Integer[]{2017, 2018, 2019}));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(18, 19, 0, 147);
        panel.add(yearBox, gridBagConstraints);

        monthBox.setModel(new DefaultComboBoxModel<>(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(21, 19, 0, 147);
        panel.add(monthBox, gridBagConstraints);

        typeLabel.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        typeLabel.setText("Loại chi phí");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(86, 55, 0, 0);
        panel.add(typeLabel, gridBagConstraints);

        yearLabel.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        yearLabel.setText("Năm");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(22, 55, 0, 0);
        panel.add(yearLabel, gridBagConstraints);

        monthLabel.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        monthLabel.setText("Tháng");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(22, 55, 0, 0);
        panel.add(monthLabel, gridBagConstraints);

        createBtn.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        createBtn.setText("Tạo");
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createChart((Integer) yearBox.getSelectedItem(), (Integer) monthBox.getSelectedItem(),
                        (String) typeBox.getSelectedItem(), chartPanel);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(51, 2, 16, 0);

        panel.add(createBtn, gridBagConstraints);

        cancelBtn.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        cancelBtn.setText("Hủy");
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(51, 18, 16, 0);
        panel.add(cancelBtn, gridBagConstraints);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void cancelBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void createChart( Integer year, Integer month, String content,ChartPanel chartPanel) {
        if (SwingUtilities.getWindowAncestor(chartPanel) instanceof BarChart) {
            BarChart barChart = new BarChart(year,month,content);
        } else {
            PieChart pieChart = new PieChart(year,month,content);
        }
        SwingUtilities.getWindowAncestor(chartPanel).dispose();
    }

    private JButton createBtn;
    private JButton cancelBtn;
    private JPanel panel;
    private JComboBox<Integer> monthBox;
    private JLabel monthLabel;
    private JComboBox<String> typeBox;
    private JLabel typeLabel;
    private JComboBox<Integer> yearBox;
    private JLabel yearLabel;

}
