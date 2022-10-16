package ru.yarm.eshop5.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yarm.eshop5.Models.Pay_method;

@Repository
public interface Pay_methodRepository extends JpaRepository<Pay_method, Long> {
}
