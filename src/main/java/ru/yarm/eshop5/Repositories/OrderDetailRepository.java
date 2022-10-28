package ru.yarm.eshop5.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yarm.eshop5.Models.OrderDetails;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Long> {
}
