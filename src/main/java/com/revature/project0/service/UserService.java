package com.revature.project0.service;

import com.revature.project0.dao.DatabaseUserDao;
import com.revature.project0.models.User;
import com.revature.project0.util.JDBCUtility;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    private final DatabaseUserDao userDao;

    public UserService() {
        this.userDao = new DatabaseUserDao();
    }

    public ArrayList<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public User getUser(int id) {
        return userDao.getUser(id);
    }

    public User insertUser(User newUser) {
        String passwordToHash = newUser.getPassword();
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md != null;
        md.update(salt);
        byte[] hashedPass = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));

        return userDao.insertUser(newUser , Hex.encodeHexString(salt) ,Hex.encodeHexString(hashedPass));
    }

    public User updateUser(User user , User newFields) {
        return userDao.updateUser(user , newFields);
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    public boolean login(User userTryingToLogin) {
        ArrayList<User> users = userDao.getAllUsers();
        return users.contains(userTryingToLogin);
    }

    public String hashingMethod(String password , String salt) {
        byte[] decodedSalt = null;
        try {
            decodedSalt = Hex.decodeHex(salt);
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md != null;
        md.update(decodedSalt);
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(hashedPassword);
    }

    public boolean authUser(User userTryingToLogin, String password) {
        String sql = "select salt, password from users where name = ?";

        try(PreparedStatement pstmt = JDBCUtility.getConnection().prepareStatement(sql)) {
            pstmt.setString(1 , userTryingToLogin.getUserName());
            ResultSet rs = pstmt.executeQuery();

            rs.next();
            String hashedPass = rs.getString("password");

            return hashedPass.equals(hashingMethod(password, userTryingToLogin.getSalt()));

        } catch (SQLException throwables) {
            return false;
//            throwables.printStackTrace();
        }
    }

    /**
     * Returns the user with the name that matches userName and returns a blank user name if none exist.
     *
     * @param userName The name of the user you would like the find
     * @return User returns the user if found and returns a blank user if not found
     */
    public User getUserByName(String userName) {
        ArrayList<User> users = userDao.getAllUsers();
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return new User();
    }
}
