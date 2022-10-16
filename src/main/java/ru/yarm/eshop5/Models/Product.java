package ru.yarm.eshop5.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Products")
public class Product {
    private static final String SEQ_NAME = "product_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @Size(min = 1, max = 255, message = "Название товара не может быть пустым")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Поле цена не должно быть пустым")
    @Min(value = 0, message = "Цена не может быть меньше нуля")
    @Column(name = "price")
    private Double price;

    @NotNull(message = "Поле вес не должно быть пустым")
    @Min(value = 0, message = "Вес не может быть меньше нуля")
    @Column(name = "weight")
    private Double weight;


    @Column(name = "date_manufactured")
    private LocalDate date_manufactured;

    @Column(name = "date_expire")
    private LocalDate date_expire;

    @Column(name = "active")
    private boolean active;

    @NotNull(message = "Категория не может быть пустой")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Transient
    @Pattern(regexp = "(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.((?:19|20)[0-9][0-9])",message = "Используйте формат даты: ЧЧ.ММ.ГГГГ")
    private String str_manufacture;

    @Transient
    @Pattern(regexp = "(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.((?:19|20)[0-9][0-9])",message = "Используйте формат даты: ЧЧ.ММ.ГГГГ")
    private String str_expire;


    public String getActiveStatus(){
        if (this.active){
            return "Да";
        }
        else {return "Нет";}
    }



    public String getManufacture(){
        LocalDate localDate=this.date_manufactured;
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return localDate.format(formatter);
    }


    public String getExpire(){
        LocalDate localDate=this.date_expire;
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return localDate.format(formatter);
    }





}
