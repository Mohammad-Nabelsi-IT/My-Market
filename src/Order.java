import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Order {
    private int id;
    private int costumerId;
    private Date orderTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(int costumerId) {
        this.costumerId = costumerId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Order() {

    }

    public static void addOrder(int id, String date) {

        try {
            DB db = new DB("ecommerce");

//            String qry = "insert into orders(id,order_time)values (" + id + ",'" + date + "')";
////            db.getStatement().executeUpdate(qry);
            PreparedStatement preparedStatement = db.getConnection().prepareStatement("insert into orders(id,order_time)values(?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, date);
            preparedStatement.executeUpdate();

            db.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getLastOrderId() {
        try {
            DB db = new DB("ecommerce");
            ResultSet resultSet = db.getStatement().executeQuery("select max(id) m from orders");
            int newOrderId = 1;
            if (resultSet.next()) {
                newOrderId = resultSet.getInt("m") + 1;
            }
            db.close();
            return newOrderId;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
