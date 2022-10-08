package ru.yarm.eshop5.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "Поле дата изготовления не должно быть пустым")
    @Column(name = "date_manufactured")
    private LocalDate date_manufactured;

    @NotNull(message = "Поле дата окончания ср.год. не должно быть пустым")
    @Column(name = "date_expire")
    private LocalDate date_expire;

    @Column(name = "active")
    private boolean active;
}
