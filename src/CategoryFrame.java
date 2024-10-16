import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryFrame extends JInternalFrame {
    private DefaultTableModel dtm;
    private JComboBox<Category> categoryJComboBox;

    public CategoryFrame() {
        setClosable(true);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);

        setTitle("Maim Category");
        setBounds(300, 150, 600, 500);
        JPanel panel = new JPanel(new GridLayout(2, 3, 2, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JLabel labelName = new JLabel("Name");
        JLabel labelCategory = new JLabel("Category");
        JTextField textFieldName = new JTextField();
        JButton buttonSave = new JButton("Save");
        categoryJComboBox = new JComboBox<>(Category.listCategories().toArray(new Category[0]));

        panel.add(labelName);
        panel.add(labelCategory);
        panel.add(new JLabel());
        panel.add(textFieldName);
        panel.add(categoryJComboBox);
        panel.add(buttonSave);

        add(panel, BorderLayout.NORTH);

        String[] cName = {"id", "name", "main Category"};
        dtm = new DefaultTableModel(null, cName);
        fillCatTable();
        JTable table = new JTable(dtm);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        buttonSave.addActionListener(e -> {
            try {
                DB db = new DB("ecommerce");
                Category selectedCat = (Category) categoryJComboBox.getSelectedItem();
                String qry = "";
                if (selectedCat == null) {
                    qry = "insert into categories(name ) values('" + textFieldName.getText() + "')";
                } else {
                    qry = "insert into categories(name ,category_id) values('" + textFieldName.getText() + "','" + selectedCat.getId() + "')";
                }
                // insert into categories(name ,category_id) values('','')
                db.getStatement().executeUpdate(qry);
                db.close();
                fillCatTable();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }


        });
    }

    private void fillCatTable() {
        // delete all data in table
        dtm.setRowCount(0);
        DB db = null;
        try {
            db = new DB("ecommerce");
            ResultSet resultSet = db.getStatement().executeQuery("SELECT c.id , c.name ,c.category_id" +
                    " ,m.name main_cat from " +
                    "categories c left join categories m on (c.category_id =m.id)");
            while (resultSet.next()) {
                String[] row = {
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("main_cat")
                };
                dtm.addRow(row);
            }

            db.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
