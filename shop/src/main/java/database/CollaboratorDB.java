package database;

import model.Collaborator;
import utils.FileUtils;

import java.util.ArrayList;

public class CollaboratorDB {
    public static ArrayList<Collaborator> collaborators = FileUtils.getCollaboratorDataFromFile("collaborator.json");
}
