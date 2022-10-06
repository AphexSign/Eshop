package ru.yarm.eshop5.Services;

import org.springframework.stereotype.Service;
import ru.yarm.eshop5.DTO.CartDTO;
import ru.yarm.eshop5.DTO.CartDetailDTO;
import ru.yarm.eshop5.Models.*;
import ru.yarm.eshop5.Repositories.CartRepository;
import ru.yarm.eshop5.Repositories.OrderRepository;
import ru.yarm.eshop5.Repositories.ProductRepository;
import ru.yarm.eshop5.Repositories.UserRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Cart createCart(User user, List<Long> productId){
        Cart cart=new Cart();
        cart.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productId);
        cart.setProducts(productList);
        return cartRepository.save(cart);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productId) {
        //Сформируем коллекцию продуктов
        List<Product> productList =new ArrayList<>();
        for(Long l:productId){
            productList.add(productRepository.getReferenceById(l));
        }
        return productList;
    }
    @Transactional
    public void addProducts(Cart cart, List<Long> productId){
        List<Product> productsList=cart.getProducts();
        List<Product> tmpProductList=null;

                if(productsList==null){
                    tmpProductList=new ArrayList<>();
                }
                else{
                    tmpProductList=new ArrayList<>(productsList);
                }
                tmpProductList.addAll(getCollectRefProductsByIds(productId));
                cart.setProducts(tmpProductList);
                cartRepository.save(cart);

    }

    public CartDTO getCartByUser(String userName){
        //Find current User
        User user=userRepository.findByName(userName).get();

        //User not found or UserCart is absent, make New TMPcart
        if(user == null || user.getCart() == null){
            return new CartDTO();
        }

        CartDTO cartDTO = new CartDTO();
        Map<Long, CartDetailDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getCart().getProducts();
        for (Product product : products) {
            CartDetailDTO detail = mapByProductId.get(product.getId());
            if(detail == null){
                mapByProductId.put(product.getId(), new CartDetailDTO(product));
            }
            else {
                detail.setAmount(detail.getAmount() + 1.0);
                detail.setSum(detail.getSum() + product.getPrice());

            }
        }
        cartDTO.setCartDetails(new ArrayList<>(mapByProductId.values()));
        cartDTO.aggregate();
        return cartDTO;
    }

    @Transactional
    public void removeFromUserCart(Long productId,String userName){
        //Получить Юзера
        User user=userRepository.findByName(userName).get();
        if(user == null){
            throw new RuntimeException("User is not found");
        }
        //Получили корзину
        Cart tmpCart=cartRepository.getReferenceById(user.getId());
        List<Product> listProducts = tmpCart.getProducts();
        listProducts.removeIf(product -> product.getId().equals(productId));
    }

    @Transactional
    public void commitCartToOrder(String userName){

        //Находим Юзера
        User user=userRepository.findByName(userName).get();
        if(user == null){
            throw new RuntimeException("User is not found");
        }
        Cart cart = user.getCart();
        if(cart == null || cart.getProducts().isEmpty()){
            return;
        }
        //Создаем новый заказ
        Order order = new Order();
        //Даем ему новый статус
        order.setStatus(OrderStatus.NEW);
        //Назначаем заказу текущего юзера
        order.setUser(user);
        Map<Product, Long> productWithAmount = cart.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        List<OrderDetails> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> new OrderDetails(order, pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());

        BigDecimal total = new BigDecimal(orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .mapToDouble(BigDecimal::doubleValue).sum());

        order.setDetails(orderDetails);
        //Заполняем суммы в заказе
        order.setSum(total);
        //Даем адрес никакой
        order.setAddress(user.getAddress());

        orderRepository.save(order);

        //Уничтожаем корзину
        cart.getProducts().clear();
        cartRepository.save(cart);



    }





}



