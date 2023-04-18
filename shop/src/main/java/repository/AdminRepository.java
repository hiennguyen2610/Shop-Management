package repository;

import database.AdminDB;
import model.Admin;
import utils.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class AdminRepository {
    public ArrayList<Admin> findAll() {
        return AdminDB.admins;
    }
    public void updateFile(ArrayList<Admin> admin) {
        FileUtils.setDataToFile("admin.json", admin);
    }
}
