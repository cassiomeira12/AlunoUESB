package com.navan.app.alunouesb.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class BaseUser implements Serializable {
    @SerializedName("_id")
    public String uID;
    @SerializedName("avatarURL")
    public String avatarURL;
    @SerializedName("status")
    public Status status;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("emailVerified")
    public boolean emailVerified;
    @SerializedName("password")
    public String password;
    @SerializedName("phone")
    public String phone;
    @SerializedName("createAt")
    public Date createAt;
    @SerializedName("updateAt")
    public Date updateAt;

    public BaseUser() {

    }

    @NonNull
    @Override
    public String toString() {
        return "BaseUser" +
                "\nid ".concat(uID) +
                "\nname ".concat(name) +
                "\nemail ".concat(email) +
                "\nemailVerified ".concat(String.valueOf(emailVerified));
    }
}
