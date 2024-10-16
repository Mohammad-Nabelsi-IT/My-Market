import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersFrame extends JInternalFrame {
    private DefaultTableModel dtm;
    private JTextField textFieldName;
    private JTextField textFieldEmail;
    private JTextField textFieldMobile;
    private JPasswordField textFieldPas;
    private JComboBox<String> roleCombo;

    private boolean editAble = true;

    public UsersFrame(User loginUser) {
        setClosable(true);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);

        setTitle("User Manager");
        setBounds(300, 150, 600, 500);

        String[] colsName = {"id", "name", "email", "mobile", "role", "created_at", "created_by"};

        dtm = new DefaultTableModel(null, colsName);
        fillUserTable();
        JTable table = new JTable(dtm);

        String arrRole[] = {"admin", "editor", "user"};
        JComboBox<String> rolecb = new JComboBox<>(arrRole);
        TableColumn columnRole = table.getColumnModel().getColumn(4);
        columnRole.setCellEditor(new DefaultCellEditor(rolecb));

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

        JLabel labelName = new JLabel("Name");
        JLabel labelEmail = new JLabel("Email");
        JLabel labelPas = new JLabel("Password");
        JLabel labelMobile = new JLabel("Mobile");
        JLabel labelRole = new JLabel("Role");
        JLabel label = new JLabel("");

        textFieldName = new JTextField();
        textFieldEmail = new JTextField();
        textFieldPas = new JPasswordField();
        textFieldMobile = new JTextField();
        roleCombo = new JComboBox<>(arrRole);

        JButton buttonAdd = new JButton("Add User");
        JButton buttonDelete = new JButton("Delete User");

        JPanel panel = new JPanel(new GridLayout(2, 5, 2, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(labelName);
        panel.add(labelEmail);
        panel.add(labelPas);
        panel.add(labelMobile);
        panel.add(labelRole);
        panel.add(label);
        panel.add(textFieldName);
        panel.add(textFieldEmail);
        panel.add(textFieldPas);

        panel.add(textFieldMobile);
        panel.add(roleCombo);
        panel.add(buttonAdd);
        getContentPane().add(panel, BorderLayout.NORTH);

        JPanel panelSouth = new JPanel();
        panelSouth.add(buttonDelete);
        this.getContentPane().add(panelSouth, BorderLayout.SOUTH);

        buttonAdd.addActionListener(e -> {
            editAble = false;
            try {

                DB db = new DB("ecommerce");

                db.getStatement().executeUpdate("insert into Users(name,email,mobile,role,created_by,password) values" +
                        "('" + textFieldName.getText() + "', '" + textFieldEmail.getText() + "', '"
                        + textFieldMobile.getText() + "','" + roleCombo.getSelectedItem() + "'," + loginUser.getId() + ",'"+textFieldPas.getText()+"')");
                db.close();

                fillUserTable();


            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });

        buttonDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            String userID = (String) dtm.getValueAt(row, 0);
            DB db = null;
            try {
                editAble = false;
                db = new DB("ecommerce");
                db.getStatement().executeUpdate("delete from users where id=" + userID);
                db.close();
                dtm.removeRow(row);
            } catch (ClassNotFoundException ex) {
                System.out.println("ClassNotFoundException : " + ex.getMessage());
            } catch (SQLException exp) {
                System.out.println("SQLException : " + exp.getMessage());

            }
            editAble = true;
        });

        table.getModel().addTableModelListener(e -> {
            if (editAble) {

                int row = e.getFirstRow();
                int col = e.getColumn();
                String value = (String) table.getValueAt(row, col);
                String userID = (String) table.getValueAt(row, 0);
                try {

                    DB db = new DB("ecommerce");

                    db.getStatement().executeUpdate("update users  set " +
                            colsName[col] + " ='" + value + "' where id=" + userID);
                    db.close();

                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    void fillUserTable() {
        dtm.setRowCount(0);
        DB db = null;
        try {
            db = new DB("ecommerce");
            ResultSet resultSet = db.getStatement().executeQuery
                    ("select u.id, u.name, u.email, u.mobile, u.role, u.created_at, u.created_by," +
                            "c.name creator_name from users u left join users c on(u.created_by=c.id)");
            while (resultSet.next()) {
                String[] row = {
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("mobile"),
                        resultSet.getString("role"),
                        resultSet.getString("created_at"),
                        resultSet.getString("creator_name")
                };
                dtm.addRow(row);
            }
            db.close();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
