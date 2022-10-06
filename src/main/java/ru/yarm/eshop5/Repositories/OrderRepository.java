package ru.yarm.eshop5.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yarm.eshop5.Models.Order;
import ru.yarm.eshop5.Models.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {



}
