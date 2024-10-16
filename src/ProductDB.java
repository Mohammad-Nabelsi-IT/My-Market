import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ProductDB {
    private int id, price, qty, category_id, user_id;
    private String name, comments, image, color, brand ,size;
    private Date created_at;

    public ProductDB() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    static ArrayList<ProductDB> listProducts() {
        ArrayList<ProductDB> listProducts = new ArrayList<>();
        listProducts.add(null);

        try {
            DB db = new DB("ecommerce");
            ResultSet resultSet = db.getStatement().executeQuery("select id, name, price, qty, comments, image," +
                    " color, brand, size, category_id, user_id, created_at from Products");
            //id, name, price, qty, comments, image, color, brand, size, category_id, user_id, created_at from Products
            while (resultSet.next()) {
                ProductDB pDB = new ProductDB();
                pDB.id = resultSet.getInt("id");
                pDB.price = resultSet.getInt("price");
                pDB.name = resultSet.getString("name");
                pDB.qty = resultSet.getInt("qty");
                pDB.comments = resultSet.getString("comments");
                pDB.color = resultSet.getString("color");
                pDB.brand = resultSet.getString("brand");
                pDB.size = resultSet.getString("size");
                pDB.category_id = resultSet.getInt("category_id");
                pDB.user_id = resultSet.getInt("user_id");
                pDB.created_at = resultSet.getDate("created_at");
                listProducts.add(pDB);
            }
            db.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listProducts;


    }

    static ArrayList<ProductDB> listProducts(int category_id) {
        ArrayList<ProductDB> listProducts = new ArrayList<>();
        listProducts.add(null);

        try {
            DB db = new DB("ecommerce");
            ResultSet resultSet = db.getStatement().executeQuery("select id, name," +
                    " price, qty, comments, image," +
                    " color, brand, size, category_id," +
                    "user_id, created_at from Products where category_id=" + category_id);
            while (resultSet.next()) {
                ProductDB pDB = new ProductDB();
                pDB.id = resultSet.getInt("id");
                pDB.name = resultSet.getString("name");
                pDB.price = resultSet.getInt("price");
                pDB.qty = resultSet.getInt("qty");
                pDB.comments = resultSet.getString("comments");
                pDB.color = resultSet.getString("color");
                pDB.brand = resultSet.getString("brand");
                pDB.size = resultSet.getString("size");
                pDB.category_id = resultSet.getInt("category_id");
                pDB.user_id = resultSet.getInt("user_id");
                pDB.created_at = resultSet.getDate("created_at");
                listProducts.add(pDB);
            }
            db.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listProducts;


    }

    @Override
    public String toString() {
        return name + "- " + price;
    }
}
