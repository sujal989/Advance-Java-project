package virinchi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private double price;


    private String category;


    @Column(length = 1000)
    private String description;


    private int stock;


    /*
     * TEMPORARY:
     * Existing image path such as:
     * images/product-1.jpg
     *
     * We keep this for now so the current
     * website continues working while we
     * build database image upload.
     */
    private String image;


    /*
     * ACTUAL IMAGE DATA
     *
     * @Lob tells Hibernate that this field
     * contains a large amount of binary data.
     *
     * In TiDB/MySQL this will be stored
     * as a BLOB/LONGBLOB.
     */
    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    @JsonIgnore
    private byte[] imageData;


    /*
     * Stores the type of the image.
     *
     * Examples:
     * image/jpeg
     * image/png
     * image/webp
     */
    @Column(name = "image_type")
    private String imageType;


    // =========================
    // GETTERS AND SETTERS
    // =========================

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public int getStock() {
        return stock;
    }


    public void setStock(int stock) {
        this.stock = stock;
    }


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }


    public byte[] getImageData() {
        return imageData;
    }


    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }


    public String getImageType() {
        return imageType;
    }


    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}