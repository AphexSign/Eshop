package ru.yarm.eshop5.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class Images {


    private static final String SEQ_NAME = "images_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "originalfilename")
    private String originalFileName;
    @Column(name = "size")
    private Long size;

    @Column(name = "contenttype")
    private String contentType;

   @Lob
   @Type(type="org.hibernate.type.BinaryType")
   @Column(name="content")
    private byte[] content;

//    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//    @JoinColumn(name = "news_id")
//    private News news;

}
