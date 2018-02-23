package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.service.SpringBootAuditService;
import com.example.vo.DataVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;  
@RestController  
public class SpringBootAuditController {  
	
    @Autowired  
    SpringBootAuditService springBootAuditService;
	
	 /*@Autowired  
	 JdbcTemplate jdbc;*/
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
    @RequestMapping("/select")  
    public List<Map<String,Object>> index(){
    	System.out.println("hiiiii");
    	return springBootAuditService.getData();
    }
    
    @RequestMapping(value ="/insertupdate" , method={RequestMethod.POST})
    public @ResponseBody String insertUpdateData(@RequestBody DataVO dataVO) throws Exception{
    	System.out.println("hiiiii going to insert/update data");
    	ObjectMapper objectMapper = new ObjectMapper();
    	boolean flag = springBootAuditService.isDataPresent(dataVO.getAppID());
    	String response = "";
    	if(flag) {
    		//update
    		if(springBootAuditService.updateData(dataVO)) {
    			response = "Data updated successfully";
    		}else {
    			response = "Data updation failed";
    		}
    	}else {
    		//insert
    		if(springBootAuditService.insertData(dataVO)) {
    			response = "Data inserted successfully";
    		}else {
    			response = "Data insertion failed ";
    		}
    	}
    	return objectMapper.writeValueAsString(response);
    	//return springBootAuditService.insertUpdateData(dataVO);
    }  
    
    @RequestMapping(value ="/delete" , method={RequestMethod.POST})
    public String delete(@RequestBody String appID){
    	System.out.println("hiiiii i am inside delet");
    	return springBootAuditService.deleteData(appID);
    } 
    
   

	/*public List<Map<String, Object>> getData() {
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
	}*/
}  
