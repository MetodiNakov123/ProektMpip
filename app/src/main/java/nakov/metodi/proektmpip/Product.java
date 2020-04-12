package nakov.metodi.proektmpip;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("product_image")
    private String product_image;

    @SerializedName("product_price")
    private String product_price;

    @SerializedName("product_title")
    private String product_title;

    @SerializedName("user_email")
    private String user_email;

    public Product() {
    }

    public Product(String product_image, String product_price, String product_title, String user_email) {
        this.product_image = product_image;
        this.product_price = product_price;
        this.product_title = product_title;
        this.user_email = user_email;
    }


    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
