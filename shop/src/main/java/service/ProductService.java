package service;

import model.Product;
import repository.ProductRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    ProductRepository productRepository = new ProductRepository();

    ArrayList<Product> ALL_Product = productRepository.allProduct();
    //hiện thông tin sản phẩm
    public ArrayList<Product> showProduct() {
        return ALL_Product;
    }

    public List<Product> showAllProduct() {
        return ALL_Product;
    }



    public int getIdProduct() {
        int max = 0;
        for (Product product: ALL_Product) {
            if (product.getId() > max) {
                max = product.getId();
            }
        }
        return max + 1;
    }
    public ArrayList<Product> findProductBycategory(String category) {
        ArrayList<Product> listProduct = new ArrayList<>();
        for (Product product : ALL_Product) {
            if (product.getCategory().toLowerCase().contains(category.toLowerCase())) {
                listProduct.add(product);
            }
        }
        return listProduct;
    }

    //thêm theo tên
    public ArrayList<Product> findProductByName(String name) {
        ArrayList<Product> listProduct = new ArrayList<>();
        for (Product product : ALL_Product) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                listProduct.add(product);
            }
        }
        return listProduct;
    }



    public void addProduct(Product newProduct) {
        ALL_Product.add(newProduct);
        productRepository.updateFile(ALL_Product);
    }

    public void updatePrice(int id, long newPrice) {
        for (Product product: ALL_Product) {
            if (product.getId() == id) {
                product.setPrice(newPrice);
            }
        }
        productRepository.updateFile(ALL_Product);
    }
    public Product findProductById(int id) {
        Product product = new Product();
        for (Product product1: ALL_Product) {
            if (product1.getId() == id) {
                product = product1;
            }
        }
        return product;
    }
    public boolean checkIdExit(int id) {
        for (Product product: ALL_Product) {
            if (product.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean checkNameExit(String name) {
        for (Product product: ALL_Product) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void updateQuantity(int id, long newQuantity) {
        for (Product product: ALL_Product) {
            if (product.getId() == id) {
                product.setQuantity(newQuantity);
            }
        }
        productRepository.updateFile(ALL_Product);
    }



    public long getCurrentQuantity(String name) {
        long currentQuantity = 0;
        for (Product product: ALL_Product) {
            if (product.getName().equalsIgnoreCase(name)) {
                currentQuantity = product.getQuantity();
            }
        }
        return currentQuantity;

    }
    public void setQuantity(String name, long newQuantity) {
        for (Product product: ALL_Product) {
            if (product.getName().equalsIgnoreCase(name)) {
                product.setQuantity(newQuantity);
            }
        }
        productRepository.updateFile(ALL_Product);
    }

    public void deleteProduct(int id) {
        ALL_Product.removeIf(a -> a.getId() == id);
        productRepository.updateFile(ALL_Product);
    }
    public ArrayList<Product> showProductInStock(long checkQuantity) {
        return (ArrayList<Product>) ALL_Product.stream()
                .filter(n -> n.getQuantity() < checkQuantity)
                .sorted(Comparator.comparing(Product::getQuantity))
                .collect(Collectors.toList());
    }
    public  ArrayList<Product> showProductByCategory(String category) {
        ArrayList<Product> listProduct = new ArrayList<>();
        for (Product product: ALL_Product) {
            if (product.getCategory().toLowerCase().contains(category.toLowerCase())) {
                listProduct.add(product);
            }
        }
        return listProduct;
    }

    public ArrayList<Product> showProductByGender(String gender) {
        ArrayList<Product> listProduct = new ArrayList<>();
        for (Product product: ALL_Product) {
            if (product.getGender().equalsIgnoreCase(gender)) {
                listProduct.add(product);
            }
        }
        return listProduct;
    }


    public ArrayList<Product> findByName(String name) {
        ArrayList<Product> listProduct = new ArrayList<>();
        for (Product product: ALL_Product) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                listProduct.add(product);
            }
        }
        return listProduct;
    }
}
