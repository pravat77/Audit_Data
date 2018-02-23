package com.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.SpringBootAuditDao;
import com.example.vo.DataVO;
//@EntityScan("com.example.service.SpringBootAuditDao")
@Service
public class SpringBootAuditService {
	
	@Autowired
	private SpringBootAuditDao springBootAuditDao;

	public List<Map<String,Object>> getData() {
		try{
			return springBootAuditDao.getData();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isDataPresent(String appID) {
		try{
			return springBootAuditDao.isDataPresent(appID);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean updateData(DataVO dataVO) {
		try{
			return springBootAuditDao.updateData(dataVO);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean insertData(DataVO dataVO) {
		try{
			return springBootAuditDao.insertData(dataVO);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public String deleteData(String appID) {
		try{
			 springBootAuditDao.deleteData(appID);
			 return "successfullly deleted";
		}catch(Exception e){
			e.printStackTrace();
			return "deletion failed";
		}
	}

	

}
