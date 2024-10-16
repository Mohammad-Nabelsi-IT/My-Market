import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Market extends JFrame {
    private JDesktopPane desktopPane;

    private User loginUser;

    public Market(User loginUser, LoginFrame loginFrame) {
        this.loginUser = loginUser;
        desktopPane = new JDesktopPane();
        getContentPane().add(desktopPane);

        setTitle("Welcome " + loginUser.getName());
        setBounds(50, 50, 1200, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        menuBar.setBackground(Color.cyan);

        JMenu menuMange = new JMenu("Mange");
        menuBar.add(menuMange);

        JMenu menuCm = new JMenu("Customers and Orders");
        menuBar.add(menuCm);


        JMenuItem menuItemUsers = new JMenuItem("Users");
        JMenuItem menuItemAddCategory = new JMenuItem("Add Category");
        JMenuItem menuItemShowCategories = new JMenuItem("Show Categories");
        JMenuItem menuItemProducts = new JMenuItem("Products");
        JMenuItem menuItemCustomers = new JMenuItem("Customers");
        JMenuItem menuItemManegeCategory = new JMenuItem("Manege Category");
        JMenuItem menuItemOrders = new JMenuItem("Orders");
        JMenuItem menuItemLogOut = new JMenuItem("Log Out");

        menuMange.add(menuItemUsers);
        menuMange.addSeparator();
        menuMange.add(menuItemManegeCategory);
        menuMange.addSeparator();
        menuMange.add(menuItemAddCategory);
        menuMange.addSeparator();
        menuMange.add(menuItemShowCategories);
        menuMange.addSeparator();
        menuMange.add(menuItemProducts);
        menuMange.add(menuItemLogOut);
        menuCm.add(menuItemCustomers);
        menuCm.addSeparator();
        menuCm.add(menuItemOrders);

        menuItemAddCategory.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(null, "Enter Categories Name");
            if (name != null && !name.trim().equals("")) {

                try {
                    DB db = new DB("ecommerce");

                    db.getStatement().executeUpdate("insert into categories(name) values('" + name + "')");
                    db.close();

                } catch (Exception exception) {
                    System.out.println("Exception" + exception.getMessage());
                }
            }
        });

        menuItemShowCategories.addActionListener(e -> {
            String allCategories = "";
            try {
                DB db = new DB("ecommerce");

                ResultSet resultSet = db.getStatement().executeQuery("select id,name from categories");
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");

                    allCategories += (id + "  " + name + "\n");
                }
                db.close();
            } catch (Exception exception) {
                System.out.println("Exception" + exception.getMessage());
            }
            JOptionPane.showMessageDialog(null, allCategories);
        });

        menuItemUsers.addActionListener(e -> {
            UsersFrame usersFrame = new UsersFrame(loginUser);
            usersFrame.setVisible(true);
            desktopPane.add(usersFrame);
        });

        menuItemCustomers.addActionListener(e -> {
            CustomersFrame customersFrame = new CustomersFrame("Customers");
            customersFrame.setVisible(true);
            desktopPane.add(customersFrame);
        });

        menuItemProducts.addActionListener(e -> {
            Products products = new Products(loginUser);
            products.setVisible(true);
            desktopPane.add(products);
        });

        menuItemLogOut.addActionListener(e -> {
            this.setVisible(false);
            loginFrame.setVisible(true);
        });

        menuItemManegeCategory.addActionListener(e -> {
            CategoryFrame categoryFrame = new CategoryFrame();
            desktopPane.add(categoryFrame);
            categoryFrame.setVisible(true);
        });

        menuItemOrders.addActionListener(e -> {
            OrderFrame newOrder = new OrderFrame();
            desktopPane.add(newOrder);
            newOrder.setVisible(true);
        });

    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

}
