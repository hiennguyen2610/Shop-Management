package repository;

import database.CollaboratorDB;
import model.Collaborator;
import utils.FileUtils;

import java.util.ArrayList;

public class CollaboratorRepository {
    public ArrayList<Collaborator> findAll() {
        return CollaboratorDB.collaborators;
    }
    public void updateFile(ArrayList<Collaborator> collaborators) {
        FileUtils.setDataToFile("collaborator.json", collaborators);
    }
}
