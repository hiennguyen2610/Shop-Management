package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Admin;
import model.Collaborator;
import model.Product;
import service.ProductService;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {
    static Scanner sc = new Scanner(System.in);
    private static final ProductService productService = new ProductService();
    public static ArrayList<Admin> getAdminDataFromFile(String fileName) {

        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(fileName));
            Type type = new TypeToken<ArrayList<Admin>>() {
            }.getType();
            ArrayList<Admin> admins = gson.fromJson(reader, type);
            reader.close();
            return admins;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static ArrayList<Collaborator> getCollaboratorDataFromFile(String fileName) {

        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(fileName));
            Type type = new TypeToken<ArrayList<Collaborator>>() {
            }.getType();
            ArrayList<Collaborator> collaborators = gson.fromJson(reader, type);
            reader.close();
            return collaborators;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static ArrayList<Product> getProductDataFromFile(String fileName) {

        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(fileName));
            Type type = new TypeToken<ArrayList<Product>>() {
            }.getType();
            ArrayList<Product> products = gson.fromJson(reader, type);
            reader.close();
            return products;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //ghi data vaof json
    public static void setDataToFile(String fileName, Object obj) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = Files.newBufferedWriter(Paths.get(fileName));
            gson.toJson(obj, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy option cho vòng lặp
    public static int getOption() {
        boolean checkOption = false;
        int option = 0;
        while (!checkOption) {
            try {
                System.out.print("Nhập lựa chọn của bạn: ");
                option = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Hãy chọn lại!");
                continue;
            }
            checkOption = true;
        }
        return option;
    }

    public static String getDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss"));
    }


    //method định dạng in ra màn hình
    public static void printUser(Collaborator collaborator) {
        System.out.printf("%-10s %-20s %-15s %-15s %-50s \n", "Username", "Email", "Password", "Phone", "Địa chỉ");
        System.out.println("--------------------------------------------------------------------------------------");

        System.out.printf("%-10s %-20s %-15s %-15s %-50s \n", collaborator.getUserName(), collaborator.getEmail(),
                collaborator.getPassword(), collaborator.getPhone(), ", " + collaborator.getAddress().getStreet() + ", " + collaborator.getAddress().getDistrict() +
                        ", " + collaborator.getAddress().getCity());

    }
    public static void printAllUser(List<Collaborator> collaborators) {
        System.out.printf("%-10s %-20s %-15s %-15s %-50s \n", "Username", "Email", "Password", "Phone", "Địa chỉ");
        System.out.println("--------------------------------------------------------------------------------------");

        for (Collaborator collaborator : collaborators) {
            System.out.printf("%-10s %-20s %-15s %-15s %-50s \n", collaborator.getUserName(), collaborator.getEmail(),
                    collaborator.getPassword(), collaborator.getPhone(), ", " + collaborator.getAddress().getStreet() + ", " + collaborator.getAddress().getDistrict() +
                            ", " + collaborator.getAddress().getCity());

        }

    }
    public static void printProduct(List<Product> list) {
        System.out.printf("%-15s %-20s %-20s %-60s %-20s %-10s\n",
                "Id", "Category", "Gender","Name", "Price", "Quantity");
        System.out.println("----------------------------------------------------------------------------------------------");
        for (Product product : list) {
            System.out.printf("%-15s %-20s %-20s %-60s %-20s %-10s\n", product.getId(), product.getCategory(),
                    product.getGender(), product.getName(), formattingDisplay(product.getPrice()), product.getQuantity());
        }
    }


    //format tiền
    public static String formattingDisplay(long price) {
        DecimalFormat formatter = new DecimalFormat("###,###,### VND");
        return formatter.format(price);
    }

    public static int getId() {
        boolean back = false;
        int id = 0;

        while (!back) {
            try {
                System.out.println("Nhập vào id: ");
                id = Integer.parseInt(sc.nextLine());
            }catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Hãy chọn lại!");
                continue;
            }
            if (productService.checkIdExit(id)) {
                back = true;
            }else {
                System.out.println("Không có sản phẩm nào trùng id!");

            }
        }
        return id;
    }

    public static String getName() {
        boolean back = false;

        String name = " ";

        while (!back) {
            try {
                System.out.println("Nhập vào tên sản phẩm : ");
                name = sc.nextLine();
            }catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Hãy chọn lại!");
                continue;
            }
            if (productService.checkNameExit(name)) {
                back = true;
            }else {
                System.out.println("Không có sản phẩm nào trùng tên!");

            }
        }
        return name;
    }
}
