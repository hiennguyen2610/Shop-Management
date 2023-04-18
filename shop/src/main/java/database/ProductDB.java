package database;

import model.Product;
import utils.FileUtils;

import java.util.ArrayList;

public class ProductDB {
    public static ArrayList<Product> products = FileUtils.getProductDataFromFile("product.json");
}
