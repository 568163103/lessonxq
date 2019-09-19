package com.beyeon.general.security.model;

import org.springframework.stereotype.Component;

import com.beyeon.general.security.model.bpo.MenuBpo;
import com.beyeon.general.security.model.bpo.RoleBpo;
import com.beyeon.general.security.model.bpo.UserBpo;
import com.beyeon.nvss.log.model.bpo.UserTraceBpo;

/**
 * User: Administrator
 * Date: 2016/4/22
 * Time: 11:26
 */
@Component("securityFacade")
public class SecurityFacade {
	private UserBpo userBpo;
	private RoleBpo roleBpo;
	private MenuBpo menuBpo;
	private UserTraceBpo userTraceBpo;

	public UserBpo getUserBpo() {
		return userBpo;
	}

	public void setUserBpo(UserBpo userBpo) {
		this.userBpo = userBpo;
	}

	public RoleBpo getRoleBpo() {
		return roleBpo;
	}

	public void setRoleBpo(RoleBpo roleBpo) {
		this.roleBpo = roleBpo;
	}

	public MenuBpo getMenuBpo() {
		return menuBpo;
	}

	public void setMenuBpo(MenuBpo menuBpo) {
		this.menuBpo = menuBpo;
	}

	public UserTraceBpo getUserTraceBpo() {
		return userTraceBpo;
	}

	public void setUserTraceBpo(UserTraceBpo userTraceBpo) {
		this.userTraceBpo = userTraceBpo;
	}
	
	
}
