package virinchi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import virinchi.model.Product;
import virinchi.repository.ProductRepository;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    // ============================================
    // GET ALL PRODUCTS
    // ============================================

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    // ============================================
    // GET PRODUCT BY ID
    // ============================================

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }


    // ============================================
    // ADD PRODUCT
    // ============================================

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }


    // ============================================
    // UPDATE PRODUCT
    // ============================================

    public Product updateProduct(
            int id,
            Product updatedProduct
    ) {

        Product existingProduct =
                productRepository
                        .findById(id)
                        .orElse(null);


        if (existingProduct == null) {
            return null;
        }


        existingProduct.setName(
                updatedProduct.getName()
        );


        existingProduct.setPrice(
                updatedProduct.getPrice()
        );


        existingProduct.setCategory(
                updatedProduct.getCategory()
        );


        existingProduct.setDescription(
                updatedProduct.getDescription()
        );


        existingProduct.setStock(
                updatedProduct.getStock()
        );


        existingProduct.setImage(
                updatedProduct.getImage()
        );


        return productRepository.save(
                existingProduct
        );
    }


    // ============================================
    // DELETE PRODUCT
    // ============================================

    public boolean deleteProduct(int id) {

        if (!productRepository.existsById(id)) {
            return false;
        }


        productRepository.deleteById(id);

        return true;
    }


    // ============================================
    // UPLOAD PRODUCT IMAGE
    // ============================================

    public Product uploadProductImage(
            int productId,
            MultipartFile file
    ) throws IOException {

        Product product =
                productRepository
                        .findById(productId)
                        .orElse(null);


        if (product == null) {
            return null;
        }


        // Store actual image bytes
        product.setImageData(
                file.getBytes()
        );


        // Store image type
        // Example: image/jpeg
        product.setImageType(
                file.getContentType()
        );


        /*
         * Once an image is uploaded,
         * we can point the frontend
         * to our backend image endpoint.
         *
         * Example:
         * /api/products/5/image
         */
        product.setImage(
                "/api/products/"
                        + productId
                        + "/image"
        );


        return productRepository.save(
                product
        );
    }


    // ============================================
    // GET PRODUCT IMAGE
    // ============================================

    public byte[] getProductImage(
            int productId
    ) {

        Product product =
                productRepository
                        .findById(productId)
                        .orElse(null);


        if (product == null) {
            return null;
        }


        return product.getImageData();
    }


    // ============================================
    // GET PRODUCT IMAGE TYPE
    // ============================================

    public String getProductImageType(
            int productId
    ) {

        Product product =
                productRepository
                        .findById(productId)
                        .orElse(null);


        if (product == null) {
            return null;
        }


        return product.getImageType();
    }
}