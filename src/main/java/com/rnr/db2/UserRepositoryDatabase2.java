package com.rnr.db2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.americanexpress.dbcompare.entity.User;


//@Repository
public interface UserRepositoryDatabase2 extends JpaRepository<User, Long> {
}
