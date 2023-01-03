package myapp.service;

import myapp.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    public String encrypt(String password);
    UserModel getUserByUsername(String username);

    List<UserModel> getListUser();

    UserModel getUserById(String id);

    void deleteUser(UserModel user);

    Boolean checkOldPassword(UserModel user, String password);

    void changePassword(UserModel user, String password);

    Boolean checkNewPassword(String password);
}
