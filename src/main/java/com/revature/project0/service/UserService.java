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

    /**
     * inserts a user and hashes their password
     * @param newUser the new user
     * @return the user object that was inserted
     */
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

    /**
     * Used for hashing a users password on a login attempt
     * @param password The password of the user trying to login
     * @param salt the users salt from the DB
     * @return String of the hashed password
     */
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

    /**
     * Checks to see if the user trying to login has a password that matches.
     * @param userTryingToLogin The user trying to login
     * @param password The users password given at login
     * @return True if matches and false if not or if SQLException is thrown
     */
    public boolean authUser(User userTryingToLogin, String password) {
        String hashedPass = userTryingToLogin.getHashedPass();
        return hashedPass.equals(hashingMethod(password, userTryingToLogin.getSalt()));
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
