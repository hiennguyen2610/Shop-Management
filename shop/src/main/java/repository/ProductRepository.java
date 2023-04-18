package repository;

import database.ProductDB;
import model.Product;
import utils.FileUtils;

import java.util.ArrayList;

public class ProductRepository {
    public ArrayList<Product> allProduct() {
        return ProductDB.products;
    }
    public void updateFile(ArrayList<Product> products) {
        FileUtils.setDataToFile("product.json", products);
    }
}
