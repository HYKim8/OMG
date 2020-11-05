package com.omg.employee.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omg.cmn.Search;
import com.omg.employee.dao.EmployeeDao;
import com.omg.employee.domain.EmployeeVO;

@Service("employeeServiceImpl")
public class EmployeeServiceImpl  {
	final Logger LOG=LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private EmployeeDao employeeDao;
	
	public EmployeeServiceImpl() {}
	
	public int passwdConfirm(EmployeeVO employee) {
		return employeeDao.passwdConfirm(employee);
	}

	public int idConfirm(EmployeeVO employee) {
		return employeeDao.idConfirm(employee);
	}

	public List<EmployeeVO> doSelectList(Search search) {
		return employeeDao.doSelectList(search);
	}

	public List<EmployeeVO> doSelectAll(EmployeeVO employee) {
		return employeeDao.doSelectAll(employee);
	}
//
//	public int count(EmployeeVO employee) {
//		return employeeDao.count(employee);
//	}

	public int doUpdate(EmployeeVO employee) {
		return employeeDao.doUpdate(employee);
	}

	public EmployeeVO doSelectOne(EmployeeVO employee) {
		return employeeDao.doSelectOne(employee);
	}

	public int doDelete(EmployeeVO employee) {
		return employeeDao.doDelete(employee);
	}

	public int doInsert(EmployeeVO employee) {
		return employeeDao.doInsert(employee);
	}
	/**
	 * 로그인시 아이디 존재 여부
	 * @param employee
	 * @return 1(존재하지 않는 아이디),0(존재하는 아이디) 
	 */
	public int idCheck(EmployeeVO employee) {
		int flag=0;
		int idFlag=employeeDao.idConfirm(employee);
		if(idFlag==1) {
			LOG.debug("이미 존재하는 아이디입니다.");
		}else {
			LOG.debug("존재하지 않는 아이디입니다.");
			flag=1;
		}
		return flag;
	}
	/**
	 * 로그인
	 * @param employee
	 * @return
	 */
	public int doLogin(EmployeeVO employee) {
		int flag=0;

		int idFlag=employeeDao.idConfirm(employee);
		
		
		if(idFlag!=1) { //아이디 존재 여부
			LOG.debug("아이디가 없습니다.");
			return flag;
		}else {
			int passwdFlag=employeeDao.passwdConfirm(employee);
			if(passwdFlag!=1) {//비밀번호 존재 여부
				LOG.debug("비밀번호가 맞지 않습니다.");
			}else {
				LOG.debug("로그인에 성공하셨습니다.");
				flag=1;
			}
			
		}
		
		return flag;
	}

}
