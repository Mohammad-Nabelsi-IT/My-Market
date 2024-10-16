import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Products extends JInternalFrame {
    private JComboBox<Category> categoryJComboBox;
    private JColorChooser colorChooser;
    private DefaultTableModel dtm;

    private boolean editAble = true;

    public Products(User loginUser) {
        setClosable(true);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);

        setTitle("Products");
        setBounds(300, 50, 600, 500);


        JTabbedPane tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);

        JPanel panelAdd = new JPanel(new GridLayout(10, 2, 10, 10));
        panelAdd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelAdd.setBackground(Color.orange);
        tabbedPane.addTab("Add Product", panelAdd);

        JPanel panelShow = new JPanel(new BorderLayout());
        panelShow.setBackground(Color.GRAY);
        tabbedPane.addTab("Show Products", panelShow);

        String clName[] = {"id", "name", "price", "qty", "image", "comments"
                , "brand", "color", "size", "user_id", "category_id", "created_at"};

        dtm = new DefaultTableModel(null, clName);
        fillProductsTable();
        JTable table = new JTable(dtm);
        JScrollPane scrollPane = new JScrollPane(table);
        panelShow.add(scrollPane);

        JLabel labelName = new JLabel("Name");
        JLabel labelPrice = new JLabel("Price");
        JLabel labelQty = new JLabel("Quantity");
        JLabel labelBrand = new JLabel("Brand");
        JLabel labelColor = new JLabel("Color");
        JLabel labelSize = new JLabel("Size");
        JLabel labelCategory = new JLabel("Category");
        JLabel labelImage = new JLabel("Image");


        JTextField tfName = new JTextField();
        JTextField tfPrice = new JTextField();
        JTextField tfQty = new JTextField();
        JTextField tfBrand = new JTextField();
        JTextField tfSize = new JTextField();


        JPanel panelFile = new JPanel(new GridLayout(1, 2, 5, 5));
        JTextField tfImage = new JTextField();
        tfImage.setEditable(false);
        tfImage.setBackground(Color.WHITE);
        JButton buttonImage = new JButton("choose image");
        panelFile.add(tfImage);
        panelFile.add(buttonImage);

        buttonImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int choise = fileChooser.showOpenDialog(null);
            if (choise == 0) {
                tfImage.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        JButton buttonAddProduct = new JButton("Add Product");
        JButton buttonDelete = new JButton("Delete");
        JButton buttonClear = new JButton("Clear");

        buttonAddProduct.setBackground(Color.green);
        buttonDelete.setBackground(Color.red);
        buttonClear.setBackground(Color.red);

        JPanel panelSouth = new JPanel(new GridLayout());
        panelSouth.add(buttonDelete);
        panelShow.add(panelSouth, BorderLayout.SOUTH);


        buttonAddProduct.addActionListener(e -> {
            Category category = (Category) categoryJComboBox.getSelectedItem();
            int catID = category.getId();
            try {
                editAble = false;
                DB db = new DB("ecommerce");
                String qry = "insert into products (name, price,qty,image,brand,size,category_id,user_id) values ('" + tfName.getText() + "','" + tfPrice.getText() + "'," +
                        "'" + tfQty.getText() + "','" + tfImage.getText() + "','" + tfBrand.getText() + "'," +
                        "'" + tfSize.getText() + "','" + catID + "'," + loginUser.getId() + ")";
                //System.out.println(qry);
                db.getStatement().executeUpdate(qry);

                db.close();
                fillProductsTable();
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
            String ProductsID = (String) dtm.getValueAt(row, 0);
            DB db = null;
            try {
                db = new DB("ecommerce");
                db.getStatement().executeUpdate("delete from Products where id=" + ProductsID);
                db.close();
                dtm.removeRow(row);
                editAble = true;
            } catch (ClassNotFoundException ex) {
                System.out.println("ClassNotFoundException : " + ex.getMessage());
            } catch (SQLException exp) {
                System.out.println("SQLException : " + exp.getMessage());

            }
        });
        buttonClear.addActionListener(e -> {
            tfName.setText(null);
            tfPrice.setText(null);
            tfImage.setText(null);
            tfBrand.setText(null);
            tfQty.setText(null);
            tfSize.setText(null);
            categoryJComboBox.setSelectedIndex(0);
            colorChooser.setChooserPanels(null);
        });

        colorChooser = new JColorChooser();
        categoryJComboBox = new JComboBox<>(Category.listCategories().toArray(new Category[0]));
        panelAdd.add(labelName);
        panelAdd.add(tfName);
        panelAdd.add(labelPrice);
        panelAdd.add(tfPrice);
        panelAdd.add(labelQty);
        panelAdd.add(tfQty);
        panelAdd.add(labelBrand);
        panelAdd.add(tfBrand);
        panelAdd.add(labelColor);
        panelAdd.add(colorChooser);
        panelAdd.add(labelSize);
        panelAdd.add(tfSize);
        panelAdd.add(labelCategory);
        panelAdd.add(categoryJComboBox);
        panelAdd.add(labelImage);
        panelAdd.add(panelFile);
        panelAdd.add(buttonAddProduct);
        panelAdd.add(buttonClear);


    }


    void fillProductsTable() {
        dtm.setRowCount(0);
        DB db = null;
        try {
            db = new DB("ecommerce");
            ResultSet resultSet = db.getStatement().executeQuery
                    ("select p.id, p.name, p.price,p.qty,p.image,p.comments,p.created_at, p.brand, p.color,p.size, p.category_id " +
                            ",p.user_id,c.name category_name\n" +
                            " from products p join categories c on(p.category_id= c.id);");
            while (resultSet.next()) {

                String[] row = {
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("price"),
                        resultSet.getString("qty"),
                        resultSet.getString("image"),
                        resultSet.getString("comments"),
                        resultSet.getString("brand"),
                        resultSet.getString("color"),
                        resultSet.getString("size"),
                        resultSet.getString("user_id"),
                        resultSet.getString("category_name"),
                        resultSet.getString("created_at"),


                };
                dtm.addRow(row);
            }
            db.close();

        } catch (
                ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (
                Exception e) {
            System.out.println("Show all : " + e.getMessage());
        }


    }


}