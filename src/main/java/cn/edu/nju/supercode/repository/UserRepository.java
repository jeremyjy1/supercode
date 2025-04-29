package cn.edu.nju.supercode.repository;

import cn.edu.nju.supercode.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}