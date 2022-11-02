package ru.yarm.eshop5.Services;

import org.springframework.stereotype.Service;
import ru.yarm.eshop5.Models.*;
import ru.yarm.eshop5.Repositories.ProductRepository;
import ru.yarm.eshop5.Repositories.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private UserRepository userRepository;
    private CartService cartService;

    public ProductService(ProductRepository productRepository, UserRepository userRepository, CartService cartService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
    }


    // Метод получающий все доступные товары, если таковые имеются
    // Выдает все продукты - пригодится только для АДМИНСКОЙ части
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> getProductActive() {

        //Получаем все продукты в порядке ID;
        List<Product> activeProduct = new ArrayList<>();
        List<Product> products = productRepository.findAllByOrderByIdAsc();

        for (Product product : products) {
            if (product.isActive()) {
                activeProduct.add(product);
            }
        }
        return activeProduct;
    }

    public List<Product> getAllByOrderByIdAsc() {


        List<Product> productList=productRepository.findAllByOrderByIdAsc();

        for(Product product:productList){
            product.setStr_manufacture(product.getManufacture());
            product.setStr_expire(product.getExpire());
        }

        return productRepository.findAllByOrderByIdAsc();

    }


    // Метод отдающий в корзину товар по id-товара и user.name, для текущей сессии
    @Transactional
    public void addToUserCart(Long productId, String name) {
        //Find the current User
        User user = userRepository.findByName(name).get();
        //  System.err.println("Found user (inner method):" + user.getName());
        if (user == null) {
            throw new RuntimeException("User not found. " + name);
        }
        //Find the Cart of User
        Cart cart = user.getCart();
        //if Cart isn't present, create new Cart by sending User and only one single product
        if (cart == null) {
            Cart newCart = cartService.createCart(user, Collections.singletonList(productId));
            user.setCart(newCart);
            //Save changes to DB
            userRepository.save(user);
        } else {
            //If cart is present, add Single Product to Cart
            cartService.addProducts(cart, Collections.singletonList(productId));
        }
    }


    //Сделать метод по выдачи продуктов только с АКТИВНЫМ статусом
    //Нужен для клиентской части

    //Метод добавки Продукта в общую базу
    //Статус Active - true!
    @Transactional
    public void addProductToDB(Product product) {
        //Устанавливаем продукту активность

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        product.setActive(false);
        product.setDate_manufactured(LocalDate.parse(product.getStr_manufacture(),formatter));
        product.setDate_expire(LocalDate.parse(product.getStr_expire(),formatter));

        productRepository.save(product);
    }

    @Transactional
    public void deactiveProd(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.setActive(false);
        productRepository.save(product);
    }

    @Transactional
    public void activeProd(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.setActive(true);
        productRepository.save(product);
    }


    public Product getProductByID(Long id) {
        Product product=productRepository.findById(id).get();
        //Для работы с датами
        product.setStr_manufacture(product.getManufacture());
        product.setStr_expire(product.getExpire());

        return product;
    }

    @Transactional
    public void updateProductToDB(Product product) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        
        product.setDate_manufactured(LocalDate.parse(product.getStr_manufacture(),formatter));
        product.setDate_expire(LocalDate.parse(product.getStr_expire(),formatter));

        product.setActive(true);
        productRepository.save(product);
    }


    public List<Product> getProductsByCategory(Long id) {

        List<Product> tmpProductList=new ArrayList<>();
        //Будем заполнять только теми у кого есть нужна категория
        List<Product> fullProductlist=productRepository.findAllByOrderByIdAsc();

        for(Product product:fullProductlist){
            if(product.getCategory().getId().equals(id)&&product.isActive()){
                tmpProductList.add(product);
            }
        }
        return tmpProductList;
    }
    @Transactional
    public void deleteProd(Long id) {
        productRepository.delete(getProductByID(id));
    }
}
