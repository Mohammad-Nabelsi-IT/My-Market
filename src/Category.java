import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Category {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    static ArrayList<Category> listCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(null);

        try {
            DB db = new DB("ecommerce");
            ResultSet resultSet = db.getStatement().executeQuery("select id,name from categories");
            while (resultSet.next()) {
                Category c = new Category();
                c.id = resultSet.getInt("id");
                c.name = resultSet.getString("name");
                categories.add(c);
            }
            db.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return categories;


    }

    static ArrayList<Category> listMainCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(null);

        try {
            DB db = new DB("ecommerce");
            ResultSet resultSet = db.getStatement().executeQuery("select id,name from categories where category_id is null");
            while (resultSet.next()) {
                Category c = new Category();
                c.id = resultSet.getInt("id");
                c.name = resultSet.getString("name");
                categories.add(c);
            }
            db.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return categories;


    }

    static ArrayList<Category> listSubCategories(int cid) {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(null);

        try {
            DB db = new DB("ecommerce");
            ResultSet resultSet = db.getStatement().executeQuery("select id,name from categories where category_id=" + cid);
            while (resultSet.next()) {
                Category c = new Category();
                c.id = resultSet.getInt("id");
                c.name = resultSet.getString("name");
                categories.add(c);
            }
            db.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return categories;


    }
}


