package controller;

import model.Admin;
import model.Collaborator;
import request.CollaboratorRequest;
import service.AdminService;
import service.CollaboratorService;

import java.util.Scanner;

public class Menu {
    Scanner  sc = new Scanner(System.in);
    CollaboratorService collaboratorService = new CollaboratorService();
    ClientController clientController = new ClientController();
    AdminService adminService = new AdminService();
    AdminController adminController = new AdminController();

    public void showMenuLogin() {
        System.out.println("\n-----------------------------------------");
        System.out.println("------- WELCOME TO ADIDAS -------");

        System.out.println("""
                1. Đăng nhập
                2. Quên mật khẩu
                0. Thoát ứng dụng
                """);
    }

    public void runMenu() {
        boolean isQuit = false;
        while (!isQuit) {
            showMenuLogin();

            int option = clientController.getOption();

            switch (option) {
                case 1 -> {
                    System.out.println("\n----------- ĐĂNG NHẬP -----------");
                    logIn();
                }
                case 2 -> {
                    System.out.println("\n----------- QUÊN MẬT KHẨU -----------");
                    forgetPassword();
                }
                case 0 -> {
                    System.out.println("\n----------- HẸN GẶP LẠI! -----------");
                    isQuit = true;
                }
                default -> System.out.println("Lựa chọn không tồn tại. Hãy chọn lại!");
            }
        }
    }

    //1.LOGIN
    public void logIn() {
        String email = getEmailToLogin();
        if (adminService.checkEmailExist(email)) {
            Admin admin = getAdminAfterCheckPass(email);
            adminController.adminLoginSuccess(admin);
        } else {
            Collaborator clientLogin = getCollaboratorAfterCheckPass(email);
            clientController.clientLoginSuccess(clientLogin);
        }
    }


    private String getEmailToLogin() {
        boolean checkEmail = false;
        String email = " ";

        while (!checkEmail) {
            System.out.println("Nhập email của bạn: ");
            email = sc.nextLine();
            if (collaboratorService.checkEmailValid(email)) {
                if (collaboratorService.checkEmailExist(email)) {
                    checkEmail = true;
                } else if (adminService.checkEmailExist(email)) {
                    checkEmail = true;
                } else {
                    System.out.println("Email không tồn tại!");
                }
            }else {
                System.out.println("Email không hợp lệ!");
            }
        }
        return email;
    }
    // Kiểm tra password theo email bên trên, nếu đúng -> lấy ra cộng tác viên

    public Admin getAdminByEmail(String email) {
        return adminService.getAdminByEmail(email);
    }
    public Admin getAdminAfterCheckPass(String email) {
        Admin admin = new Admin();
        boolean checkPassword= false;
        while (!checkPassword) {
            System.out.print("Nhập password: ");
            String password = sc.nextLine();
            if (password.equals(getAdminByEmail(email).getPassword())) {
                admin = getAdminByEmail(email);
                checkPassword = true;
            }else {
                System.out.println("Password của bạn không chính xác!");
            }
        }
        return admin;
    }
    public Collaborator getCollaboratorAfterCheckPass(String email) {
        Collaborator collaborator = new Collaborator();
        boolean checkPassword = false;
        while (!checkPassword) {
            System.out.print("Nhập password: ");
            String password = sc.nextLine();
            if (password.equals(collaboratorService.getCollaboratorByEmail(email).getPassword())) {
                collaborator = collaboratorService.getCollaboratorByEmail(email);
                checkPassword = true;
            }else {
                System.out.println("Password của bạn không chính xác!");
            }
        }
        return collaborator;
    }

    //2. quên mk
    public void forgetPassword() {
        String email = getEmailToLogin();
        System.out.println("Hãy nhập mật khẩu mới cho tài khoản của bạn!");
        String password = getPasswordToRegister();

        CollaboratorRequest changePasswordRequest = new CollaboratorRequest(email, password);
        collaboratorService.changePassword(changePasswordRequest);
        System.out.println("Thay đổi password thành công!");
    }
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

}
