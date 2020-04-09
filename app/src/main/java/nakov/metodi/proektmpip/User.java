package nakov.metodi.proektmpip;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("firstName")
    public String firstName;

    @SerializedName("lastName")
    public String lastName;

    @SerializedName("email")
    public String email;

    @SerializedName("contactno")
    public String contactno;

    public User() {
    }

    public User(String firstName, String lastName, String email, String contactno) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactno = contactno;
    }
}
