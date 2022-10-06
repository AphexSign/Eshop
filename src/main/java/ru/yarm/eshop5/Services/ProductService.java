package ru.yarm.eshop5.Services;

import org.springframework.stereotype.Service;
import ru.yarm.eshop5.Models.Cart;
import ru.yarm.eshop5.Models.Product;
import ru.yarm.eshop5.Models.User;
import ru.yarm.eshop5.Repositories.ProductRepository;
import ru.yarm.eshop5.Repositories.UserRepository;

import javax.transaction.Transactional;
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

    // Все методы, связанные с продуктовой страницей

    // Метод получающий все доступные товары, если таковые имеются
    public List<Product> getAll(){
        return productRepository.findAll();
    }

    // Метод отдающий в корзину товар по id-товара и user.name, для текущей сессии
    @Transactional
    public void addToUserCart(Long productId, String name) {
        //Find the current User
        User user=userRepository.findByName(name).get();
        System.err.println("Found user (inner method):"+user.getName());
        if(user == null){
            throw new RuntimeException("User not found. " + name);
        }
        //Find the Cart of User
        Cart cart=user.getCart();
        //if Cart isn't present, create new Cart by sending User and only one single product
        if(cart==null){
            Cart newCart=cartService.createCart(user, Collections.singletonList(productId));
            user.setCart(newCart);
            //Save changes to DB
            userRepository.save(user);
        } else{
            //If cart is present, add Single Product to Cart
            cartService.addProducts(cart,Collections.singletonList(productId));
        }

    }
}
