package tuan.tidi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tuan.tidi.DTO.StatusDTO;
import tuan.tidi.jwt.JWTService;

@Service
public class CheckJWT {
	
	@Autowired 
	private JWTService jwtService;
	
	public StatusDTO checkJWT(String authToken, Boolean checkAdmin) {
		StatusDTO statusDTO = new StatusDTO();
		//check valid token
		if(!jwtService.validateTokenLogin(authToken)) {
			statusDTO.setMessage("Invalid Token!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}

		//check token expired
//		if(jwtService.isTokenExpired(authToken)) {
//			statusDTO.setMessage("Expired!!!");
//			statusDTO.setStatus("FALSE");
//			return statusDTO;
//		}
		//check admin permissionjwtService.getPermission(authToken)
		if(checkAdmin == true && !jwtService.getPermission(authToken).equals("ROLE_ADMIN")) {
			statusDTO.setMessage("You must be an admin!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}	
		statusDTO.setStatus("TRUE");
		statusDTO.setMessage("Successful!");
		return statusDTO;
	}
}
