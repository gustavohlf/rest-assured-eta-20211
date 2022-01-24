package models;

import com.google.gson.JsonObject;

public class User {
    public String nome;
    public String email;
    public String password;
    public String _id;
    public String administrador;
    private String authorization;

    public User(String nome, String email, String password, String administrador ){
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
    }

    public void setUserId(String id){
        this._id = id;
    }

    public String getUserCredentials(){
        JsonObject userCredentials = new JsonObject();
        userCredentials.addProperty("email", this.email);
        userCredentials.addProperty("password", this.password);
        System.out.println(userCredentials.toString());
        return userCredentials.toString();
    }

    public void setAuthorization(String authorization){
        this.authorization = authorization;
    }

    public String getAuthorization(){
        return this.authorization;
    }
}
