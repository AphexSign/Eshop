package ru.yarm.eshop5.Services;

import org.springframework.stereotype.Service;
import ru.yarm.eshop5.Models.Cart;
import ru.yarm.eshop5.Models.Product;
import ru.yarm.eshop5.Models.Role;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Repositories.ProductRepository;
import ru.yarm.eshop5.Repositories.UserRepository;

import javax.transaction.Transactional;
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

        return productRepository.findAllByOrderByIdAsc();

    }


    // Метод отдающий в корзину товар по id-товара и user.name, для текущей сессии
    @Transactional
    public void addToUserCart(Long productId, String name) {
        //Find the current User
        User user = userRepository.findByName(name).get();
        System.err.println("Found user (inner method):" + user.getName());
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
        product.setActive(true);
        //Записываем продукт в базу
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
        return productRepository.findById(id).get();
    }

    public void updateProductToDB(Product product) {
        //Получаем ID - записываем туда данные продукта и записываем его по ID
        product.setActive(true);
        productRepository.save(product);
    }
}
