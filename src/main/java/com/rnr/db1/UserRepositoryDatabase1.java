package com.rnr.db1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.americanexpress.dbcompare.entity.User;

//@Repository
public interface UserRepositoryDatabase1 extends JpaRepository<User, Long> {
}
