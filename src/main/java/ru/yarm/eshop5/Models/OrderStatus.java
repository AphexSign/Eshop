package ru.yarm.eshop5.Models;

//New - при создании нового заказа(Возможно его не нужно указывать
//Reserved - как только товар поступил из корзины в Order_detail
//Cancelled - на этапе управления заказом - оплата картой/оплата при получении наличными/отмена
//Closed - Когда Paid перешел в разряд Доставлен. Установлен менеджером.


public enum OrderStatus {NEW("Новый"), RESERVED("Зарезервирован"), PAID("Оплачено"), CANCELLED("Отменен"),CLOSED("Завершен");

    private String title;
    private OrderStatus(String title){
        this.title=title;
    }

    public String getTitle(){
        return this.title;
    }



}

