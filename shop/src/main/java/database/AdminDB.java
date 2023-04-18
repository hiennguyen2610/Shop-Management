package database;

import model.Admin;
import utils.FileUtils;

import java.util.ArrayList;

public class AdminDB {
    public static ArrayList<Admin> admins = FileUtils.getAdminDataFromFile("admin.json");
}
