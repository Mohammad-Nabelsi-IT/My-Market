import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class LoginFrame extends JFrame {
    public LoginFrame(String title) {
        setTitle("Login...");
        setBounds(400, 150, 500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel labelEmail = new JLabel("Email");
        JLabel labelPas = new JLabel("Password");

        JTextField textFieldEmail = new JTextField("admin@gmail.com");
        textFieldEmail.setPreferredSize(new Dimension(250, 30));
        JPasswordField passwordField = new JPasswordField("123");
        passwordField.setPreferredSize(new Dimension(250, 30));
        JButton buttonLogin = new JButton("Login");

        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        add(labelEmail, c);
        c.gridx = 1;
        c.gridy = 0;
        add(textFieldEmail, c);

        c.gridx = 0;
        c.gridy = 1;
        add(labelPas, c);
        c.gridx = 1;
        c.gridy = 1;
        add(passwordField, c);

        c.gridx = 1;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(buttonLogin, c);

        JLabel labelMassage = new JLabel();
        labelMassage.setForeground(Color.red);
        c.gridx = 1;
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(labelMassage, c);

        buttonLogin.addActionListener(e -> {
            // check email and password

            String email = textFieldEmail.getText().trim();
            String pas = passwordField.getText();
            try {
                DB db = new DB("ecommerce");
                ResultSet resultSet = db.getStatement().executeQuery("select * from users " +
                        "where email='" + email + "' and " + "password='" + pas + "'");
                if (resultSet.next()) {

                    User loginUser = new User(email, pas);
                    loginUser.setId(resultSet.getInt("id"));
                    loginUser.setName(resultSet.getString("name"));
                    loginUser.setMobile(resultSet.getString("mobile"));
                    loginUser.setRole(resultSet.getString("role"));
                    loginUser.setCreatedBy(resultSet.getInt("created_by"));
                    loginUser.setCreatedAt((Date) resultSet.getObject("created_at"));

                    new Market(loginUser,this).setVisible(true);
                    this.setVisible(false);
                } else {
                    labelMassage.setText("Invalid email or password.");
                }
                db.close();


            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }


        });

    }

    public static void main(String[] args) {

        LoginFrame loginFrame = new LoginFrame("Login...");
        loginFrame.setVisible(true);
    }
}
