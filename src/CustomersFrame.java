import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersFrame extends JInternalFrame {

    private DefaultTableModel dtm;
    private boolean editAble = true;

    public CustomersFrame(String title) {
        setClosable(true);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);

        setTitle("Customers");
        setBounds(300, 200, 800, 500);

        String[] colName = {"id", "name", "email",
                "password", "phone", "street_no", "street", "postal_code", "address", "created_at"};


        dtm = new DefaultTableModel(null, colName);
        fillCustomerTable();
        JTable table = new JTable(dtm);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

        JLabel labelName = new JLabel("Name");
        JLabel labelEmail = new JLabel("Email");
        JLabel labelPassword = new JLabel("password");
        JLabel labelPhone = new JLabel("phone");
        JLabel labelStreetNo = new JLabel("street_no");
        JLabel labelStreet = new JLabel("street");
        JLabel labelPostalCode = new JLabel("postal_code");
        JLabel labelAddress = new JLabel("address");
        JLabel labelEmpty1 = new JLabel("");
        JLabel labelEmpty2 = new JLabel("");

        JTextField textFieldName = new JTextField();
        JTextField textFieldEmail = new JTextField();
        JTextField textFieldPassword = new JTextField();
        JTextField textFieldPhone = new JTextField();
        JTextField textFieldStreetOn = new JTextField();
        JTextField textFieldStreet = new JTextField();
        JTextField textFieldPostalCode = new JTextField();
        JTextField textFieldAddress = new JTextField();

        JButton buttonAdd = new JButton("Add Customers");
        JButton buttonDelete = new JButton("Delete Customers");

        JPanel panelNorth = new JPanel(new GridLayout(4, 5, 3, 3));
        panelNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelNorth.add(labelName);
        panelNorth.add(labelEmail);
        panelNorth.add(labelPassword);
        panelNorth.add(labelPhone);
        panelNorth.add(labelEmpty1);
        panelNorth.add(textFieldName);
        panelNorth.add(textFieldEmail);
        panelNorth.add(textFieldPassword);
        panelNorth.add(textFieldPhone);
        panelNorth.add(buttonAdd);
        panelNorth.add(labelStreetNo);
        panelNorth.add(labelStreet);
        panelNorth.add(labelPostalCode);
        panelNorth.add(labelAddress);
        panelNorth.add(labelEmpty2);
        panelNorth.add(textFieldStreetOn);
        panelNorth.add(textFieldStreet);
        panelNorth.add(textFieldPostalCode);
        panelNorth.add(textFieldAddress);
        panelNorth.add(buttonDelete);
        getContentPane().add(panelNorth, BorderLayout.NORTH);

        buttonAdd.addActionListener(e -> {
            try {
                editAble = false;
                DB db = new DB("ecommerce");
                // id, name, email, password, phone, mobile, street_no, street, postal_code, address, created_at
                db.getStatement().executeUpdate("insert into Customers(name,email,password,phone,street_no,street," +
                        "postal_code,address) values" +
                        "('" + textFieldName.getText() + "', '" + textFieldEmail.getText() + "', '"
                        + textFieldPassword.getText() + "','" + textFieldPhone.getText() + "','" + textFieldStreetOn.getText()
                        + "','" + textFieldStreet.getText() + "','" + textFieldPostalCode.getText()
                        + "','" + textFieldAddress.getText() + "')");
                db.close();

                fillCustomerTable();
                editAble = true;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        buttonDelete.addActionListener(e -> {
            editAble = false;
            int row = table.getSelectedRow();
            String CustomerID = (String) dtm.getValueAt(row, 0);
            DB db = null;
            try {
                db = new DB("ecommerce");
                db.getStatement().executeUpdate("delete from Customers where id=" + CustomerID);
                db.close();
                dtm.removeRow(row);
                editAble = true;
            } catch (ClassNotFoundException ex) {
                System.out.println("ClassNotFoundException : " + ex.getMessage());
            } catch (SQLException exp) {
                System.out.println("SQLException : " + exp.getMessage());

            }
        });

        table.getModel().addTableModelListener(e -> {
            if (editAble) {
                int row = e.getFirstRow();
                int col = e.getColumn();
                String value = (String) table.getValueAt(row, col);
                String CustomerID = (String) table.getValueAt(row, 0);
                try {
                    DB db = new DB("ecommerce");

                    db.getStatement().executeUpdate("update Customers  set " +
                            colName[col] + " ='" + value + "' where id=" + CustomerID);
                    db.close();

                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });

    }

    void fillCustomerTable() {
        dtm.setRowCount(0);
        DB db = null;
        try {
            db = new DB("ecommerce");
            ResultSet resultSet = db.getStatement().executeQuery
                    ("select id, name, email,password, phone, street_no, street, postal_code, address, created_at from customers");
            while (resultSet.next()) {
                String[] row = {
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("street_no"),
                        resultSet.getString("street"),
                        resultSet.getString("postal_code"),
                        resultSet.getString("address"),
                        resultSet.getString("created_at"),
                };
                dtm.addRow(row);
            }
            db.close();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("Show all : " + e.getMessage());
        }
    }
}

