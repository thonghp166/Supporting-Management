package template.defaultTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        ContentTextArea[] textArea = new ContentTextArea[4];
        textArea[0] = new ContentTextArea();
        textArea[0].setName("tên gói thầu");

        textArea[1] = new ContentTextArea();
        textArea[1].setName("Ngày tờ trình");

        textArea[2] = new ContentTextArea();
        textArea[2].setName("Giá VAT");
        textArea[3] = new ContentTextArea();
        textArea[3].setName("Chi phí theo kế hoạch năm được duyệt");
        JPanel panel_grid = new JPanel();
        panel_grid.setLayout(new GridLayout(0,1,10,0));
        panel_grid.add(textArea[0]);
        panel_grid.add(textArea[1]);
        panel_grid.add(textArea[2]);
        panel_grid.add(textArea[3]);

        JScrollPane scrollPane = new JScrollPane(panel_grid, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //panel_grid.getLayout().minimumLayoutSize(scrollPane);
        frame.add(scrollPane);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Timer timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < 2; i++) {
                    textArea[0].resize();
                    textArea[1].resize();
                    textArea[2].resize();
                    textArea[3].resize();
                }
                frame.revalidate();
            }
        });
        timer.start();
    }
}
