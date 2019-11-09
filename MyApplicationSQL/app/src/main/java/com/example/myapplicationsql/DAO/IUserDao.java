package com.example.myapplicationsql.DAO;

import com.example.myapplicationsql.pojo.User;

import java.util.List;

public interface IUserDao {

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	long addUser(User user);

	/**
	 *删除用户
	 * @param id
	 * @return
	 */
	int delUserById(int id);

	/**
	 *更新用户
	 *
	 * @param user
	 * @return
	 */
	int updateUser(User user);

	/**
	 * 查询用户
	 * @param id
	 * @return
	 */
	User getUserById(int id);

	/**
	 * 获取用户列表
	 * @return
	 */
	List<User> listAllUser();
}
