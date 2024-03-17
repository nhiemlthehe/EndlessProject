/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author 12345
 */
public class Setting {
    String sever = "localhost:1433";
    String database = "EndlessDB";

    public String getSever() {
        return sever;
    }

    public void setSever(String sever) {
        this.sever = sever;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
