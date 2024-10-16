import java.sql.SQLException;

public class OrderDetail {
    private int id, orderId, productId, qty;
    private double unitPrice, tax, totalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static void addOrderDetail(int orderId, String productId, String qty, String unitPrice
            , String tax, String total) {

        try {
            DB db = new DB("ecommerce");

            String qry = "insert into order_details(order_id,product_id,qty,unit_price,tax,total_price_with_tax)values" +
                    "(" + orderId + "," + productId + "," + qty + "," + unitPrice + "," + tax + "," + total + ")";

            db.getStatement().executeUpdate(qry);
            db.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
