package ru.yarm.eshop5.Models;

public enum Role {
    CLIENT("Клиент"), MANAGER("Менеджер"), ADMIN("Администратор"), BANNED("Заблокированный");

    private String title;
    private Role(String title){
        this.title=title;
    }

    public String getTitle(){
        return this.title;
    }


}
