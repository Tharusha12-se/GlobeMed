/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Decorator;

import model.User;

/**
 *
 * @author User
 */


public abstract class UserDecorator implements User {
    protected User decoratedUser;
    
    public UserDecorator(User user) {
        this.decoratedUser = user;
    }
    
    public String getUserName() {
        return decoratedUser.getUserName();
    }
    
    @Override
    public String getFname() {
        return decoratedUser.getFname();
    }
    
    @Override
    public String getLname() {
        return decoratedUser.getLname();
    }
    
    @Override
    public int getRoleId() {
        return decoratedUser.getRoleId();
    }
    

    
    @Override
    public boolean hasPermission(String permission) {
        return decoratedUser.hasPermission(permission);
    }
}