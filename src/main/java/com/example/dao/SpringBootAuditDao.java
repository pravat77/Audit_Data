package com.example.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.example.vo.DataVO;

@Repository
public class SpringBootAuditDao {
	
	@Autowired  
    JdbcTemplate jdbc;
	
	String INSERT_DATA = "INSERT INTO TB_AUDIT (CLIENT,APP_ID,RELEASE_VERSION,DB_NAME,RELEASE_WHO,ENVIRONMENT) " +
			" VALUES ( ? ,? ,?, ?, ?, ?)";
	
	String UPDATE_DATA = "UPDATE TB_AUDIT set CLIENT=?,RELEASE_VERSION=?,DB_NAME=?,RELEASE_WHO=?,ENVIRONMENT=? where  " +
			" APP_ID = ?";
	
	String DELETE_DATA = "DELETE FROM TB_AUDIT WHERE APP_ID = ? ";
	


	public List<Map<String, Object>> getData() {
		List<Map<String,Object>> response = new ArrayList<Map<String,Object>>();
    	Map<String,Object> obj = null;
    	SqlRowSet rs = jdbc.queryForRowSet("select CLIENT,App_ID,RELEASE_VERSION,DB_Name,Release_WHO,Environment from TB_AUDIT");
    	while(rs.next()) {
    		obj = new HashMap<String,Object>();
    		obj.put("CLIENT",rs.getString("CLIENT"));
    		obj.put("App_ID",rs.getString("App_ID"));
    		//obj.put("Release_Date",rs.getDate("Release_Date"));
    		obj.put("RELEASE_VERSION",rs.getString("RELEASE_VERSION"));
    		obj.put("DB_Name",rs.getString("DB_Name"));
    		obj.put("Release_WHO",rs.getString("Release_WHO"));
    		obj.put("Environment",rs.getString("Environment"));
    		response.add(obj);
    	}
    	return response;
	}
	
	public boolean isDataPresent(String appID) {
		boolean flag = false;
		SqlRowSet rs = jdbc.queryForRowSet("select * from TB_AUDIT where App_ID=?", new Object[] {appID});
		if(rs.next()) {
			flag = true;
		}
		return flag;
	}

	public boolean updateData(DataVO dataVO) {
		
		try {
			int cnt = jdbc.update(UPDATE_DATA, new Object[]{dataVO.getClient(),  dataVO.getReleaseVersion(),
					dataVO.getDbName(), dataVO.getReleasewho(),dataVO.getEnvironment(),dataVO.getAppID()});
			if(cnt>0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean insertData(DataVO dataVO) {
		//CLIENT,APP_ID,RELEASE_VERSION,DB_NAME,RELEASE_WHO,ENVIRONMENT
		try {
			int cnt = jdbc.update(INSERT_DATA, new Object[]{dataVO.getClient(), dataVO.getAppID(), dataVO.getReleaseVersion(),
					dataVO.getDbName(), dataVO.getReleasewho(),dataVO.getEnvironment()});
			if(cnt>0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public void deleteData(String appID) {
		try {
			jdbc.update(DELETE_DATA, new Object[] {appID});
		}catch(Exception e) {
			
		}
		
	}

	

}
