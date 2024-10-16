import javax.swing.*;
import java.awt.*;

public class LayoutNew extends JFrame {
    public LayoutNew(String title) {
        setTitle("New Layout");
        setBounds(500, 100, 500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();

        JButton b1 = new JButton("One");
        JButton b2 = new JButton("Tow");
        JButton b3 = new JButton("Three");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(b1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(b2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(b3, gbc);

    }

    public static void main(String[] args) {
        LayoutNew layoutNew = new LayoutNew("New Layout");
        layoutNew.setVisible(true);

    }
}
