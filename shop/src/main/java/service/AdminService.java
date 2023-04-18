package service;

import model.Admin;
import repository.AdminRepository;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AdminService {
    AdminRepository adminRepository = new AdminRepository();

    ArrayList<Admin> ALL_ADMIN = adminRepository.findAll();


    public boolean checkEmailValid(String email) {
        String EMAIL_PATTERN =
                "^[a-zA-Z][\\w-]+@([\\w]{5}+\\.[\\w]{3,}+|[\\w]{5}+\\.[\\w]{3,}\\.[\\w]{2,})$";

        return Pattern.matches(EMAIL_PATTERN, email);
    }

    public boolean checkPasswordValid(String password) {
        String PASSWORD_PATTERN =
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{8,20})";

        return Pattern.matches(PASSWORD_PATTERN, password);
    }

    public boolean checkEmailExist(String email) {
        for (Admin admin : ALL_ADMIN) {
            if (admin.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public Admin getAdminByEmail(String email) {
        Admin admin = new Admin();
        for (Admin ad : ALL_ADMIN) {
            if (ad.getEmail().equalsIgnoreCase(email)) {
                admin = ad;
            }
        }
        return admin;
    }

    public void changeEmail(String email, String newEmail) {
        for (Admin ad : ALL_ADMIN) {
            if (ad.getEmail().equalsIgnoreCase(email)) {
                ad.setEmail(newEmail);
            }
        }
        adminRepository.updateFile(ALL_ADMIN);
    }

    public void changePassword(String email, String newPassword) {
        for (Admin ad : ALL_ADMIN) {
            if (ad.getEmail().equalsIgnoreCase(email)) {
                ad.setPassword(newPassword);
            }
        }
        adminRepository.updateFile(ALL_ADMIN);
    }
}
