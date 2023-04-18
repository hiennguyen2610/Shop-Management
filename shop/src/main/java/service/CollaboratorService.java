package service;

import model.Address;
import model.Collaborator;
import repository.CollaboratorRepository;
import request.CollaboratorRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CollaboratorService {
    CollaboratorRepository collaboratorRepository = new CollaboratorRepository();
    ArrayList<Collaborator> ALL_COLLABORATOR = collaboratorRepository.findAll();
    public ArrayList<Collaborator> allCOLLABORATOR() {
        return collaboratorRepository.findAll();
    }
    public boolean checkEmailValid(String email) {
        String EMAIL_PATTERN =
                "^[a-zA-Z][\\w-]+@([\\w]{5}+\\.[\\w]{3,}+|[\\w]{5}+\\.[\\w]{3,}\\.[\\w]{2,})$";

        return Pattern.matches(EMAIL_PATTERN, email);
    }

    public boolean checkPasswordValid(String password) {
        String PASSWORD_PATTERN =
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{6,20})";

        return Pattern.matches(PASSWORD_PATTERN, password);
    }

    public boolean checkPhoneValid(String phone) {
        String PHONE_PATTERN =
                "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))" +
                        "(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";

        return Pattern.matches(PHONE_PATTERN, phone);
    }

    public boolean checkEmailExist(String email) {
        for (Collaborator collaborator : ALL_COLLABORATOR) {
            if (collaborator.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public Collaborator getCollaboratorByEmail(String email) {
        Collaborator collaborator = new Collaborator();
        for (Collaborator collaborator1 : ALL_COLLABORATOR) {
            if (collaborator1.getEmail().equalsIgnoreCase(email)) {
                collaborator = collaborator1;
            }
        }
        return collaborator;
    }

    public void createNewCollaborator(Collaborator newCollaborator) {
        ALL_COLLABORATOR.add(newCollaborator);
        collaboratorRepository.updateFile(ALL_COLLABORATOR);
    }

    public void changePassword(CollaboratorRequest changePasswordRequest) {
        for (Collaborator collaborator : ALL_COLLABORATOR) {
            if (collaborator.getEmail().equalsIgnoreCase(changePasswordRequest.getEmail())) {
                collaborator.setPassword(changePasswordRequest.getPassword());
            }
        }
        collaboratorRepository.updateFile(ALL_COLLABORATOR);
    }

    public Address getAddress(String email) {
        for (Collaborator collaborator : ALL_COLLABORATOR) {
            if (collaborator.getEmail().equalsIgnoreCase(email)) {
                return collaborator.getAddress();
            }
        }
        return null;
    }

    public void changeUsername(String email, String newUsername) {
        for (Collaborator collaborator : ALL_COLLABORATOR) {
            if (collaborator.getEmail().equalsIgnoreCase(email)) {
                collaborator.setUserName(newUsername);
            }
        }
        collaboratorRepository.updateFile(ALL_COLLABORATOR);
    }


    public void changeAddress(String email, Address newAddress) {
        for (Collaborator collaborator : ALL_COLLABORATOR) {
            if (collaborator.getEmail().equalsIgnoreCase(email)) {
                collaborator.setAddress(newAddress);
            }
        }
        collaboratorRepository.updateFile(ALL_COLLABORATOR);
    }

    public void changePhone(String email, String newPhone) {
        for (Collaborator collaborator : ALL_COLLABORATOR) {
            if (collaborator.getEmail().equalsIgnoreCase(email)) {
                collaborator.setPhone(newPhone);
            }
        }
        collaboratorRepository.updateFile(ALL_COLLABORATOR);
    }

    public void deleteAccount(String emailToDelete) {
        ArrayList<Collaborator> toRemove = new ArrayList<>();
        for (Collaborator collaborator : ALL_COLLABORATOR) {
            if (collaborator.getEmail().equalsIgnoreCase(emailToDelete)) {
                toRemove.add(collaborator);
            }
        }
        ALL_COLLABORATOR.removeAll(toRemove);
        collaboratorRepository.updateFile(ALL_COLLABORATOR);
    }

    public List<Collaborator> showAllCollaborator() {
        return ALL_COLLABORATOR;
    }
}
