/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

import java.util.UUID;

/**
 *
 * @author Khoa Tran
 */
public class Category {
    private int id;
    private String name;
    
    
    public Category (String name) {
        this.name = name;
    }
    
    public Category (int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Category() {
        
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
