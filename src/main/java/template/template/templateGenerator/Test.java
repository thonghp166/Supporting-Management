package template.template.templateGenerator;

import template.content.Content;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        try {
            TemplateGenerator tg = new TemplateGenerator();
            JFrame frame = new JFrame();
            frame.setLayout(new BorderLayout());
            frame.add(tg, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(400, 600));
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
