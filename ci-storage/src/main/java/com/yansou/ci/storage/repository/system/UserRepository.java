package com.yansou.ci.storage.repository.system;

import com.yansou.ci.core.model.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @author liutiejun
 * @create 2017-05-02 19:42
 */
public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String name);

	User findByNameAndAge(String name, Integer age);

	@Query("select u from User u where u.name = :name")
	User findUserByName(String name);

	@Modifying
	@Query("update User u set u.name = :name, u.age = :age where u.id = :id")
	int update(String name, Integer age, Long id);

	@Transactional
	@Modifying
	@Query("update User u set u.status = ?1 where u.id = ?2")
	int updateStatus(Integer status, Long id);

	@Modifying
	@Query("update User u set u.name = :#{#user.name}, u.age = :#{#user.age} where u.id = :#{#user.id}")
	int update(User user);

}