package ru.yarm.eshop5.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yarm.eshop5.Models.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
