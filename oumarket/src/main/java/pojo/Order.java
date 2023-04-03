/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.UUID;

/**
 *
 * @author Khoa Tran
 */
public class Order {
    private String id;
    private LocalDateTime orderDate;
    private Double total;
    
    {
        setId(UUID.randomUUID().toString());
    }
    
    public Order() {
        this.orderDate = LocalDateTime.now();
    }
    
    public Order(String id, LocalDateTime orderDate, double total) {
        this.id = id;
        this.orderDate = orderDate;
        this.total = total;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the orderDate
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total = total;
    }
    
}
