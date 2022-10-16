package ru.yarm.eshop5.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yarm.eshop5.Models.Order_status;

@Repository
public interface Order_StatusRepository extends JpaRepository<Order_status, Long> {
}
