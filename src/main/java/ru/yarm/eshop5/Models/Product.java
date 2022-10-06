package ru.yarm.eshop5.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

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

    @Column(name = "title")
    @Size(min = 1, max = 255, message = "Название товара не может быть пустым")
    private String title;

    @Column(name = "price")
    @Min(value = 0, message = "Цена не может быть меньше нуля")
    private Double price;

    @Column(name = "weight")
    @Min(value = 0, message = "Вес не может быть меньше нуля")
    private Double weight;

    @Column(name = "date_manufactured")
    private LocalDate date_manufactured;

    @Column(name = "date_expire")
    private LocalDate date_expire;


}
