package ru.yarm.eshop5.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    private static final String SEQ_NAME = "order_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime changed;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private BigDecimal sum;

    @ManyToOne
    @JoinColumn(name = "pay_method_id")
    private Pay_method pay_method;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Order_status order_status;

    private String address;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderDetails> details;

    public Pay_method getPay_method() {
        return pay_method;
    }


    //    @Enumerated(EnumType.STRING)
//    private OrderStatus status;




    public String getCreatedTime(){
        LocalDateTime localDateTime=this.created;
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public String getChangedTime(){
        LocalDateTime localDateTime=this.changed;
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss");
        return localDateTime.format(formatter);
    }

//    public String getPayMethod(){
//        if (this.pay.equals("Bank")){
//            return "Безналичная";
//        }
//        else {return "Наличная";}
//    }



}