package ru.yarm.eshop5.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "news")
public class News {

    private static final String SEQ_NAME = "news_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name="message",columnDefinition="TEXT")
    private String message;

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime changed;

    @Column(name = "active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<Images> images=new ArrayList<>();

    @PrePersist
    private void init() {
        created = LocalDateTime.now();
    }


    public String getAuthor(){
        return "Новость от: "+user.getName()+"___";
    }

    public String getAuthorAdmin(){
        return user.getName();
    }

    public String getCreatedTime(){
        LocalDateTime localDateTime=this.created;
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm");
        return "Опубликовано: "+localDateTime.format(formatter);
    }


    public String getCreatedTimeAdmin(){
        LocalDateTime localDateTime=this.created;
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm");
        return localDateTime.format(formatter);
    }

    public String getChangedTimeAdmin(){
        LocalDateTime localDateTime=this.changed;
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm");
        return localDateTime.format(formatter);
    }

//    public void addImageToNews(Images image) {
//        image.setNews(this);
//        images.add(image);
//    }


}
