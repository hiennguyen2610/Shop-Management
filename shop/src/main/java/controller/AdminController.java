package controller;

import model.Address;
import model.Admin;
import model.Collaborator;
import model.Product;
import service.AdminService;
import service.CollaboratorService;
import service.ProductService;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminController {
    Scanner sc = new Scanner(System.in);
    AdminService adminService = new AdminService();
    CollaboratorService collaboratorService = new CollaboratorService();
    ProductService productService = new ProductService();
    public void showMenu() {
        System.out.println("""
                \n1. Thông tin tài khoản
                2. Quản lý cộng tác viên
                3. Quản lý sản phẩm
                0. Đăng xuất
                """);

    }
    public void adminLoginSuccess(Admin admin) {
        System.out.println("\n----------- ĐĂNG NHẬP CHẾ ĐỘ ADMIN THÀNH CÔNG -----------");
        System.out.println("Xin chào Admin. Bạn có thể thực hiện: ");

        boolean isQuitLogin = false;
        while (!isQuitLogin) {
            showMenu();
            int option = getOption();

            switch (option) {
                case 1 -> {
                    System.out.println("\n---------------- QUẢN LÝ TÀI KHOẢN ----------------");
                    manageAcount(admin.getEmail());
                }
                case 2 -> {
                    System.out.println("\n---------------- QUẢN LÝ CỘNG TÁC VIÊN ----------------");
                    manageClient();
                }
                case 3 -> {
                    System.out.println("\n---------------- QUẢN LÝ SẢN PHẨM ----------------");
                    manageProduct();
                }
                case 0 -> {
                    System.out.println("\n----------- HẸN GẶP LẠI! -----------");
                    isQuitLogin = true;
                }
                default -> System.out.println("Lựa chọn không tồn tại. Hãy chọn lại!");
            }
        }
    }

    // 1. Quản lý tài khoản
    public void manageAcount(String email) {
        boolean back = false;
        while (!back) {
            System.out.println("\n1. Thay đổi email \t\t 2. Đổi mật khẩu \t\t 0. Quay lại");

            int option = getOption();

            switch (option) {
                case 1 -> {
                    changeEmail(email);
                    back = true;
                }
                case 2 -> changePassword(email);
                case 0 -> back = true;
                default -> System.out.println("Lựa chọn không tồn tại. Hãy chọn lại!");
            }
        }
    }

    public void changePassword(String email) {
        String newPassword = getPasswordToUpdate();
        adminService.changePassword(email, newPassword);
        System.out.println("Đã thay đổi mật khẩu thành công!");
    }

    public void changeEmail(String email) {
        String newEmail = getNewEmail();
        adminService.changeEmail(email, newEmail);
        System.out.println("Đã thay đổi email thành công! Yêu cầu đổi mật khẩu.");
        changePassword(newEmail);
        System.out.println("Đăng nhập lại để hoàn tất!");
    }

    // Lấy email mới
    public String getNewEmail() {
        boolean checkEmail = false;
        String email = "";
        while (!checkEmail) {
            System.out.print("Nhập email của bạn: ");
            email = sc.nextLine();
            if (adminService.checkEmailValid(email)) {
                if (collaboratorService.checkEmailExist(email) || adminService.checkEmailExist(email)) {
                    System.out.println("Email đã tồn tại!");
                } else {
                    checkEmail = true;
                }
            } else {
                System.out.println("Email không hợp lệ!");
            }
        }
        return email;
    }

    // Lấy, check password
    public String getPasswordToUpdate() {
        boolean checkPassword = false;
        String password = "";
        while (!checkPassword) {
            System.out.print("Nhập password: ");
            password = sc.nextLine();
            if (collaboratorService.checkPasswordValid(password)) {
                checkPassword = true;
            } else {
                System.out.println("Password không hợp lệ!");
                System.out.println("Password phải có ít nhất 6 ký tự gồm chữ hoa, chữ thường, số và 1 ký tự đặc biệt!");
            }
        }
        return password;
    }

    //2. Quản lý ctv
    public void manageClient() {
        boolean back = false;
        while (!back) {
            System.out.println("\n1. Hiển thị tất cả tài khoản \t\t2. Thêm tài khoản \t\t 3. Xóa tài khoản \t\t 0. Quay lại");

            int option = getOption();

            switch (option) {
                case 1 -> showAllUser();
                case 2 -> addAcount();
                case 3 -> deleteAcount();
                case 0 -> back = true;
                default -> System.out.println("Lựa chọn không tồn tại. Hãy chọn lại!");
            }
        }
    }


    // Hiển thị tất cả tài khoản
    public void showAllUser() {
        FileUtils.printAllUser(collaboratorService.showAllCollaborator());
    }

    // 2.1 thêm tài khoản
    public void addAcount() {
        System.out.print("Nhập username: ");
        String userName = sc.nextLine();

        String newEmail = getEmailToRegister();
        String password = getPasswordToRegister();
        String phone = getPhoneToRegister();
        Address address = getAddressToRegister();

        Collaborator newCollaborator = new Collaborator(userName, newEmail, password, phone, address);

        collaboratorService.createNewCollaborator(newCollaborator);
        System.out.println("Bạn đã tạo tài khoản thành công! Xin chúc mừng. ");
        FileUtils.printUser(newCollaborator);
    }

    public String getEmailToRegister() {
        boolean checkEmail = false;
        String email = "";
        while (!checkEmail) {
            System.out.print("Nhập email tài khoản: ");
            email = sc.nextLine();
            if (collaboratorService.checkEmailValid(email)) {
                if (collaboratorService.checkEmailExist(email)) {
                    System.out.println("Email đã tồn tại!");
                } else if (adminService.checkEmailExist(email)) {
                    System.out.println("Email đã tồn tại! ");
                } else {
                    checkEmail = true;
                }
            } else {
                System.out.println("Email không hợp lệ!");
            }
        }
        return email;
    }

    // Sau khi kiemr tra email chuyển qua kiểm tra password cho phần đăng ký
    // Sử dụng để lấy lại mật khẩu

    public String getPasswordToRegister() {
        boolean checkPassword = false;
        String password = "";
        while (!checkPassword) {
            System.out.print("Nhập password: ");
            password = sc.nextLine();
            if (collaboratorService.checkPasswordValid(password)) {
                checkPassword = true;
            } else {
                System.out.println("Password không hợp lệ!");
                System.out.println("Password phải có ít nhất 6 ký tự gồm chữ hoa, chữ thường, số và 1 ký tự đặc biệt!");
            }
        }
        return password;
    }

    // Tiếp đó là kiểm tra phone cho phần đăng ký
    public String getPhoneToRegister() {
        boolean checkPhoned = false;
        String phone = "";
        while (!checkPhoned) {
            System.out.print("Nhập số điện thoại của bạn: ");
            phone = sc.nextLine();
            if (collaboratorService.checkPhoneValid(phone)) {
                checkPhoned = true;
            } else {
                System.out.println("Số điện không hợp lệ!");
            }
        }
        return phone;
    }

    public Address getAddressToRegister() {
        System.out.println("Nhập địa chỉ");
        System.out.print("Thành phố: ");
        String city = sc.nextLine();

        System.out.print("Quận: ");
        String district = sc.nextLine();

        System.out.print("Đường: ");
        String street = sc.nextLine();

        return new Address(city, district, street);
    }

    // 2.2 Xóa tài khoản
    public void deleteAcount() {
        String email = getEmailToDelete();
        FileUtils.printUser(getUserByEmail(email));
        System.out.println("Bạn có muốn xóa tài khoản này?");
        System.out.println("1. Có \t\t 2. Không");
        int option = getOption();
        if (option == 1) {
            collaboratorService.deleteAccount(email);
            System.out.println("Đã xóa tài khoản thành công!");
        }
    }

    public String getEmailToDelete() {
        boolean checkEmail = false;
        String email = "";
        while (!checkEmail) {
            System.out.print("Nhập email tài khoản muốn xóa: ");
            email = sc.nextLine();
            if (collaboratorService.checkEmailValid(email)) {
                if (collaboratorService.checkEmailExist(email)) {
                    checkEmail = true;
                } else {
                    System.out.println("Email không tồn tại!");
                }
            } else {
                System.out.println("Email không hợp lệ!");
            }
        }
        return email;
    }

    public Collaborator getUserByEmail(String email) {
        return collaboratorService.getCollaboratorByEmail(email);
    }

    // 3. Quản lý sản phẩm
    public void manageProduct() {
        boolean back = false;
        while (!back) {
            System.out.println("""
                    \n1. Xem toàn bộ sản phẩm \t\t 2. Thêm sản phẩm \t\t\t 3. Sửa sản phẩm  
                    4. Xóa sản phẩm \t\t\t\t 5. Kiểm tra sản phẩm \t\t 0. Quay lại
                    """);

            int option = getOption();

            switch (option) {
                case 1 -> showProduct();
                case 2 -> addProduct();//TODO
                case 3 -> editProduct();
                case 4 -> deleteProduct();
                case 5 -> checkProductInStock();
                case 0 -> back = true;
                default -> System.out.println("Lựa chọn không tồn tại. Hãy chọn lại!");
            }
        }
    }
    //3.1 Xem sản phẩm
    public void showProduct() {
        FileUtils.printProduct(productService.showAllProduct());
    }

    //3.2 thêm sản phẩm
    public void addProduct() {
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
            } catch (NumberFormatException e) {
                System.out.println("Nhập lỗi!");
            }
        }
    }

    //theo danh mục
    public boolean checkProductExist(String category) {
        return productService.findProductBycategory(category).size() > 0;
    }

    //theo tên
    public boolean checkProductByName(String name) {
        return productService.findProductByName(name).size() > 0;
    }






    // 3.3 sửa sản phẩm
    public void editProduct() {
        boolean back = false;
        while (!back) {
            System.out.println("""
                    \n1. Sửa giá \t\t 2. Cập nhật số lượng  \t\t 0. Quay lại
                    """);

            int option = getOption();

            switch (option) {
                case 1 -> upDatePrice();
                case 2 -> updateQuantity();
                case 0 -> back = true;
                default -> System.out.println("Lựa chọn không tồn tại. Hãy chọn lại!");
            }
        }
    }

    // cập nhật giá sản phẩm
    public void upDatePrice() {
        int id = getId();
        long newPrice = getPrice();
        productService.updatePrice(id, newPrice);
        System.out.println("Đã cập nhật giá thành công!");
    }

    public int getPrice() {
        boolean back = false;
        int newPrice = 0;

        while (!back) {
            try {
                System.out.print("Nhập vào giá mới: ");
                newPrice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Hãy chọn lại!");
                continue;
            }
            back = true;
        }
        return newPrice;
    }

    // cập nhật số lượng trong kho
    public void updateQuantity() {
        int id = getId();
        long newQuantity = getQuantity();
        productService.updateQuantity(id, newQuantity);
        System.out.println("Đã cập nhật số lượng sản phẩm trong kho!");
    }

    public long  getQuantity() {
        boolean back = false;
        long newQuantity = 0;

        while (!back) {
            try {
                System.out.print("Nhập số lượng sản phẩm: ");
                newQuantity = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Hãy chọn lại!");
                continue;
            }
            back = true;
        }
        return newQuantity;
    }

    // 3.4 xóa sản phẩm
    public void deleteProduct() {
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

    //3.5 kiểm tra sản phẩm
    //kiểm tra số lượng sản phẩm trong kho
    public void checkProductInStock() {
        long checkQuantity = getQuantity();
        if (productService.showProductInStock(checkQuantity).size() > 0) {
            FileUtils.printProduct(productService.showProductInStock(checkQuantity));
        } else {
            System.out.println("Không có sản phẩm nào dưới: " + checkQuantity);
        }

    }







    //method phuj
    public int getOption() {
        return FileUtils.getOption();
    }

    public int getId() {
        return FileUtils.getId();
    }

    public String getName() {
        return FileUtils.getName();
    }
}
