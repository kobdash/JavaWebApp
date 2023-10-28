/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java 
 */



package model;
import java.util.List;



public class Product {
    private int productId;
    private String productName;
    private String description;
    private double price;
    private String categoryName;
    private List<String> imageUrls;
    private int categoryId;


    public Product() {
        // Default constructor
    }
    

    //vontructors
    public Product(int productId, String productName, String description, double price, String categoryName, List<String> imageUrls) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
        this.imageUrls = imageUrls;
    }
    
    public Product(int productId, String productName, String description, double price, String categoryName){
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
    
    };
    
    public Product(String productName, String description, double price, String categoryName){
        
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
    
    };
    
    public Product(int productId, String productName, double price, String categoryName){
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.categoryName = categoryName;
    };

    // Getters and setters
    
   public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
    
    
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

     public String getCategoryName() {
        return categoryName;
    }
     public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

     
    public int getCategoryId() {
        return categoryId;
    }


    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public String toString() {
        return "Product [productId=" + productId + ", productName=" + productName + ", description=" + description
                + ", price=" + price + ", categoryName=" + categoryName + "]";
    }
}
