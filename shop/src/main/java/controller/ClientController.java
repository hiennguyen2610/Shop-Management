package controller;

import model.Collaborator;
import model.Product;
import service.CollaboratorService;
import service.ProductService;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.Scanner;

public class ClientController {
    Scanner sc = new Scanner(System.in);
    CollaboratorService collaboratorService = new CollaboratorService();
    ProductService productService = new ProductService();

    //Khung trong Collaborator
    //Login thành công

    public void subClientMenu() {
        System.out.println("\nBạn có thể thực hiện:");
        System.out.println("""
                1. Xem danh sách sản phẩm 
                2. Tìm kiếm sản phẩm
                3. Xóa sản phẩm
                4. Thêm sản phẩm
                0. Đăng xuất                                
                """);
    }

    //Menu sau khi login thành công
    public void clientLoginSuccess(Collaborator clientLogin) {
        System.out.println("\n----------- ĐĂNG NHẬP THÀNH CÔNG -----------");
        System.out.printf("Xin chào bạn, %s. \n", clientLogin.getUserName());

        boolean isQuitLogin = false;
        while (!isQuitLogin) {
            subClientMenu();
            int option = getOption();

            switch (option) {
                case 1 -> {
                    System.out.println("\n----------- XEM SẢN PHẨM -----------");
                    showProduct(clientLogin);
                }
                case 2 -> {
                    System.out.println("\n----------- TÌM SẢN PHẨM -----------");
                    findProduct(clientLogin);
                }
                case 3 -> {
                    System.out.println("\n----------- XÓA SẢN PHẨM -----------");
                    deleteProduct(clientLogin);
                }
                case 4 -> {
                    System.out.println("\n----------- THÊM SẢN PHẨM -----------");
                    addProduct(clientLogin);
                }
                case 0 -> {
                    System.out.println("\n----------- HẸN GẶP LẠI! -----------");
                    isQuitLogin = true;
                }
                default -> System.out.println("Lựa chọn không tồn tại. Hãy chọn lại!");

            }
        }

    }

    //1. xem danh sach sản phẩm
    public void showProduct(Collaborator clientLogin) {
        ArrayList<Product> products;
        boolean backToMenu = false;

        while (!backToMenu) {
            System.out.println("""
                    \n1. Xem toàn bộ sản phẩm \t\t 2. Xem theo danh mục \t\t 3. Xem theo giới tính\t\t0. Quay lại
                    """);

            int option = getOption();

            switch (option) {
                case 1 -> {
                    products = (ArrayList<Product>) productService.showAllProduct();
                    if (products.size() != 0) {
                        printProduct(products);
                    }
                }
                case 2 -> {
                    products = showProductByCategory();
                    if (products.size() != 0) {
                        printProduct(products);
                    }
                }
                case 3 -> {
                    products = showProductByGender();
                    if (products.size() != 0) {
                        printProduct(products);
                    }
                }
                case 0 -> backToMenu = true;
                default -> System.out.println("Lựa chọn không tồn tại. Hãy chọn lại!");
            }
        }
    }

    //1.1 xem theo danh mục
    public void listCategory() {

        System.out.println("""
                \n1. Polo\t2. Shoe\t\t3. bag\t\t\t4. Accessory\t\t5. T-Shirt
          
                """);
    }

    //trả về list sản pẩm theo danh mục khi đã lấy được dữ liệu
    public ArrayList<Product> showProductByCategory() {
        boolean backToMenu = false;
        String category = " ";

        while (!backToMenu) {
            listCategory();

            int option = getOption();

            switch (option) {
                case 1 -> {
                    category = "Polo";
                    backToMenu = true;
                }
                case 2 -> {
                    category = "Shoe";
                    backToMenu = true;
                }
                case 3 -> {
                    category = "bag";
                    backToMenu = true;
                }
                case 4 -> {
                    category = "Accessory";
                    backToMenu = true;
                }
                case 5 -> {
                    category = "T-Shirt";
                    backToMenu = true;
                }
                default -> System.out.println("Lựa chọn không tồn tại. Hãy chọn lại!");

            }
        }
        return productService.showProductByCategory(category);
    }

    //1.2 xem danh sách sản phẩm theo giới tính

    public void listGender() {
        System.out.println("""
                \n1. Male\t\t2. Female
                """);
    }

    public ArrayList<Product> showProductByGender() {
        boolean backToMenu = false;
        String gender = " ";

        while (!backToMenu) {
            listGender();
            int option = getOption();

            switch (option) {
                case 1 -> {
                    gender = "Male";
                    backToMenu = true;
                }
                case 2 -> {
                    gender = "Female";
                    backToMenu = true;
                }
                default -> System.out.println("Lựa chọn không tồn tại. Hãy chọn lại!");
            }
        }
        return productService.showProductByGender(gender);
    }

    //2. Tìm kiếm sản phẩm
    public void findProduct(Collaborator clientLogin) {
        try {
            ArrayList<Product> products;
            boolean backToMenu = false;

            while (!backToMenu) {
                System.out.println("""
                        \n1. Tìm theo tên sản phẩm \t\t 2. Tìm theo danh mục \t\t 0. Quay lại
                        """);

                int option = getOption();
                switch (option) {
                    case 1 -> {
                        products = findProductByName();
                        if (products.size() != 0) {
                            printProduct(products);
                        }else {
                            System.out.println("Không tìm thấy sản phẩm");
                        }
                    }
                    case 2 -> {
                        products = findProductByCategory();
                        if (products.size() != 0) {
                            printProduct(products);
                        }else {
                            System.out.println("Không tìm thấy sản phẩm");
                        }
                    }
                    case 0 -> backToMenu = true;
                    default -> System.out.println("Lựa chọn không hợp lệ. Hãy chọn lại!");
                }
            }
        } catch (Exception e) {
            System.out.println("Hãy đăng nhập trước!");
        }
    }
    //2.1 tìm sản phẩm theo tên
    public ArrayList<Product> findProductByName() {
        System.out.println("Nhập vào tên sản phẩm: ");
        String name = sc.nextLine();
        return productService.findByName(name);
    }

    //2.2 tìm sản phẩm theo danh mục
    public ArrayList<Product> findProductByCategory() {
        System.out.println("Nhập vào danh mục: ");
        String category = sc.nextLine();
        return productService.findProductBycategory(category);
    }

    //3. Xóa sản phẩm
    public void deleteProduct(Collaborator clientLogin) {
        int id = getId();
        ArrayList<Product> findProduct = new ArrayList<>();
        findProduct.add(productService.findProductById(id));
        FileUtils.printProduct(findProduct);

        boolean back = false;
        while (!back) {
            System.out.println("\nBạn muốn xóa sản phẩm này: ");

            System.out.print("1. Có \t\t 2. Không\n");
            int option = getOption();

            switch (option) {
                case 1 -> {
                    productService.deleteProduct(id);
                    System.out.println("Bạn đã xóa thành công!");
                    back = true;
                }
                case 2 -> back = true;
                default -> System.out.println("Lựa chọn không tồn tại. Hãy chọn lại!");
            }
        }
    }
    //4. thêm sản phẩm
    public void addProduct(Collaborator clientLogin) {
        int id = productService.getIdProduct();
        System.out.print("Nhập vào tên sản phẩm:");
        String name = sc.nextLine();
        if (checkProductByName(name)) {
            System.out.println("Sản phẩm đã có trong kho!");
            System.out.println("Nhập vào số lượng: ");
            long quantity = Integer.parseInt(sc.nextLine());

            long oldQuantity = productService.getCurrentQuantity(name);
//            System.out.println("số lượng cũ: " + oldQuantity);
            long newQuantity =  oldQuantity + quantity;

            System.out.println("Số lượng sản phẩm sau khi thêm là : " + newQuantity);
            productService.setQuantity(name, newQuantity);

        } else {
            try {
                System.out.println("Nhập vào danh mục: ");
                String category = sc.nextLine();

                System.out.print("Nhập vào giới tính: ");
                String gender = sc.nextLine();

                System.out.print("Nhập vào giá: ");
                long price = Integer.parseInt(sc.nextLine());

                System.out.print("Số lượng: ");
                long quantity = Integer.parseInt(sc.nextLine());

                Product newProduct = new Product(id,category, gender, name, price, quantity);
                productService.addProduct(newProduct);
                System.out.println("Đã thêm sản phẩm thành công!");
            } catch (Exception e) {
                System.out.println("Nhập lỗi!");
            }
        }
    }


    public boolean checkProductByName(String name) {
        return productService.findProductByName(name).size() > 0;
    }

    //method phụ
    public int getOption() {
        return FileUtils.getOption();
    }

    public int getId() {
        return FileUtils.getId();
    }
    public String getName() {
        return FileUtils.getName();
    }


    public void printProduct(ArrayList<Product> products) {
        FileUtils.printProduct(products);
    }

}
