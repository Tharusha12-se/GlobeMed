///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package model;
//
//// In your UserFactory class or a utility class
//public class UserConverter {
//    
//    public static User convertToUser(User_Bean userBean) {
//        User user = new User() {};
//        user.setId(userBean.getId());
//        user.setFname(userBean.getFname());
//        user.setLname(userBean.getLname());
//        user.setEmail(userBean.getEmail());
//        user.setPassword(userBean.getPassword());
//        user.setMobile(userBean.getMobile());
//        user.setUserRoleId(userBean.getUserRoleId());
//        // Set any other fields that exist in User class
//        return user;
//    }
//    
//    public static User_Bean convertToUserBean(User user) {
//        User_Bean userBean = new User_Bean();
//        userBean.setId(user.getId());
//        userBean.setFname(user.getFname());
//        userBean.setLname(user.getLname());
//        userBean.setEmail(user.getEmail());
//        userBean.setPassword(user.getPassword());
//        userBean.setMobile(user.getMobile());
//        userBean.setUserRoleId(user.getUserRoleId());
//        // Set any other fields that exist in User_Bean class
//        return userBean;
//    }
//}