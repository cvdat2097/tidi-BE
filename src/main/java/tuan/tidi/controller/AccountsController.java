package tuan.tidi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tuan.tidi.DTO.account.AccountsDTO;
import tuan.tidi.DTO.account.LoginDTO;
import tuan.tidi.DTO.account.PasswordDTO;
import tuan.tidi.DTO.account.RegisterDTO;
import tuan.tidi.DTO.account.TokenDTO;
import tuan.tidi.DTO.StatusDTO;
import tuan.tidi.DTO.account.UsernameDTO;
import tuan.tidi.DTO.account.VeriPasswordDTO;
import tuan.tidi.DTO.account.VerificationDTO;
import tuan.tidi.DTO.account.VerifyTokenDTO;
import tuan.tidi.api.API;
import tuan.tidi.entity.Accounts;
import tuan.tidi.entity.Verification;
import tuan.tidi.service.CheckJWT;
import tuan.tidi.service.HashPassword;
import tuan.tidi.service.RandomVerificationCode;
import tuan.tidi.jwt.JWTService;
import tuan.tidi.repository.account.AccountsRepository;
import tuan.tidi.repository.account.AccountsRepositoryCustomImpl;
import tuan.tidi.repository.account.VerificationRepository;
import tuan.tidi.repository.account.VerificationRepositoryCustomImpl;
@Controller
public class AccountsController {
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private VerificationRepository verificationRepository;
	
	@Autowired
	private AccountsRepositoryCustomImpl accountsRepositoryCustomImpl;
	
	@Autowired
	private VerificationRepositoryCustomImpl verificationRepositoryCustomImpl;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private CheckJWT checkJwt;
	
	@Autowired
	private HashPassword hashPassword;
	
	//login
	@CrossOrigin(origins = "*")
	@PostMapping(API.LOGIN)
	@ResponseBody
	public LoginDTO login(@RequestBody Accounts accounts) {
		Accounts acc;
		LoginDTO loginDTO = new LoginDTO();
		acc = accountsRepository.findByUsernameLike(accounts.getUsername());
		
		if (acc == null) {
			StatusDTO statusDTO = new StatusDTO();
			statusDTO.setStatus("FALSE");
			statusDTO.setMessage("Username has not been existed!!!");
			loginDTO.setStatus(statusDTO);
			return loginDTO;
		}
		
		if (acc.getActive() == "FALSE") {
			StatusDTO statusDTO = new StatusDTO();
			statusDTO.setStatus("FALSE");
			statusDTO.setMessage("You has been block! Send me 50$ :v");
			loginDTO.setStatus(statusDTO);
			return loginDTO;
		}
		
		if (acc.getPassword().equals(hashPassword.hash(accounts.getPassword()))){
			StatusDTO statusDTO = new StatusDTO();
			statusDTO.setStatus("TRUE");
			loginDTO.setPermission(acc.getPermission());
			loginDTO.setToken(jwtService.generateTokenLogin(accounts.getUsername(), acc.getPermission()));
			statusDTO.setMessage("Successful!");
			loginDTO.setStatus(statusDTO);
			return loginDTO;
		}
		else {
			StatusDTO statusDTO = new StatusDTO();
			statusDTO.setStatus("FALSE");
			statusDTO.setMessage("Wrong password! Try again!");
			loginDTO.setStatus(statusDTO);
			return loginDTO;
		}
	}
	
	//register
	@CrossOrigin(origins = "*")
	@PostMapping(API.REGISTER)
	@ResponseBody
	public LoginDTO register(@RequestBody RegisterDTO registerDTO){
		LoginDTO loginDTO = new LoginDTO();
		StatusDTO statusDTO = new StatusDTO();
		
		//check username
		if (registerDTO.getUsername() == null || registerDTO.getUsername().isEmpty()) {
			statusDTO.setMessage("Username is null!!!");
			statusDTO.setStatus("FALSE");
			loginDTO.setStatus(statusDTO);
			return loginDTO;
		}
		if (accountsRepository.findByUsernameLike(registerDTO.getUsername()) != null) {
			statusDTO.setMessage("Username has been existed!!!");
			statusDTO.setStatus("FALSE");
			loginDTO.setStatus(statusDTO);
			return loginDTO;
		}
		//check email
		if (accountsRepository.findByEmailLike(registerDTO.getEmail()) != null) {
			statusDTO.setMessage("Email has been existed!!!");
			statusDTO.setStatus("FALSE");
			loginDTO.setStatus(statusDTO);
			return loginDTO;
		}
		if (registerDTO.getEmail() == null || registerDTO.getEmail().isEmpty()) {
			statusDTO.setMessage("Email is null!!!");
			statusDTO.setStatus("FALSE");
			loginDTO.setStatus(statusDTO);
			return loginDTO;
		}
		//password
		if (registerDTO.getPassword() == null || registerDTO.getPassword().isEmpty()) {
			statusDTO.setMessage("Password is null!!!");
			statusDTO.setStatus("FALSE");
			loginDTO.setStatus(statusDTO);
			return loginDTO;
		}
		//save
		Accounts accounts = new Accounts();
		accounts.setPassword(hashPassword.hash(registerDTO.getPassword()));
		accounts.setUsername(registerDTO.getUsername());
		accounts.setFullName(registerDTO.getFullName());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dob;
		try {
			dob = format.parse(registerDTO.getDateOfBirth());
		} catch (Exception e) {
			dob = null;
		}
		accounts.setDateOfBirth(dob);
		accounts.setGender(registerDTO.getGender());
		accounts.setPhone(registerDTO.getPhone());
		accounts.setEmail(registerDTO.getEmail());
		accounts.setAddress(registerDTO.getAddress());
		accounts.setAvatar(registerDTO.getAvatar());
		accounts.setPermission("CUSTOMER");
		accounts.setIsVerified("FALSE");
		accounts.setActive("TRUE");
		accountsRepository.save(accounts);

		//create new code
		Accounts account = accountsRepository.findByUsernameLike(accounts.getUsername());
		RandomVerificationCode rand = new RandomVerificationCode();
		Verification verification = new Verification(account.getId(), rand.randomCode() + Integer.toString(account.getId()), "EMAIL", "TRUE");
		verificationRepository.save(verification);
		
		/*SendEmail sendEmail = new SendEmail();
		try{
			sendEmail.sendEmail();
		}catch(Exception e) {
			
		}*/
		statusDTO.setStatus("TRUE");
		statusDTO.setMessage("Successful");
		loginDTO.setStatus(statusDTO);
		loginDTO.setPermission("CUSTOMER");
		loginDTO.setToken(jwtService.generateTokenLogin(accounts.getUsername(), accounts.getPermission()));
		return loginDTO;
	}
	
	//Registration email verification
	@CrossOrigin(origins = "*")
	@PostMapping(API.EMAILVERIFICATION)
	@ResponseBody
	public StatusDTO emailVerification(@RequestBody VerificationDTO verificationDTO){
		Verification verification = verificationRepositoryCustomImpl.findCodeType(verificationDTO.getVerificationCode(), "EMAIL");
		StatusDTO statusDTO = new StatusDTO();
		
		if (verification == null) {
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		Accounts accounts = accountsRepository.findById(verification.getAccountsId());
		verification.setActive("FALSE");
		accounts.setIsVerified("TRUE");
		accountsRepository.save(accounts);
		verificationRepository.save(verification);
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}
	
	//Reset password
	@CrossOrigin(origins = "*")
	@PostMapping(API.RESETPASSWORD)
	@ResponseBody
	public StatusDTO resetPassword(@RequestBody UsernameDTO usernameDTO) {
		Accounts accounts = accountsRepository.findByUsernameLike(usernameDTO.getUsername());
		StatusDTO statusDTO = new StatusDTO();
		if (accounts == null) {
			statusDTO.setStatus("FAlSE");
			statusDTO.setMessage("Username has not been existed!!!");
			return statusDTO;
		}
		
		//non-active code
		Verification veri = verificationRepositoryCustomImpl.findAccountsIdType(accounts.getId(), "PASSWORD");
		if (veri != null) {
			veri.setActive("FAlSE");
			verificationRepository.save(veri);
		}
		
		//create new code
		RandomVerificationCode rand = new RandomVerificationCode();
		Verification verification = new Verification(accounts.getId(), rand.randomCode() + Integer.toString(accounts.getId()), "PASSWORD", "TRUE");
		verificationRepository.save(verification);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}
	
	//Reset password email verification
	@CrossOrigin(origins = "*")
	@PostMapping(API.RESETEMAILVERIFICATION)
	@ResponseBody
	public StatusDTO resetPasswordEmailVerification(@RequestBody VerificationDTO verificationDTO) {
		Verification verification = verificationRepositoryCustomImpl.findCodeType(verificationDTO.getVerificationCode(), "PASSWORD");
		StatusDTO statusDTO = new StatusDTO();
		
		if (verification == null) {
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		Accounts accounts = accountsRepository.findById(verification.getAccountsId());
		verification.setActive("FALSE");
		verificationRepository.save(verification);
		
		//non-active code
		Verification veri = verificationRepositoryCustomImpl.findAccountsIdType(accounts.getId(), "NEWPASSWORD");
		if (veri != null) {
			veri.setActive("FAlSE");
			verificationRepository.save(veri);
		}
				
		//create new code
		RandomVerificationCode rand = new RandomVerificationCode();
		veri = new Verification(accounts.getId(), rand.randomCode() + Integer.toString(accounts.getId()), "NEWPASSWORD", "TRUE");
		verificationRepository.save(veri);
		
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}
	
	//READ Account information
	@CrossOrigin(origins = "*")
	@PostMapping(API.INFO)
	@ResponseBody
	public AccountsDTO readAccountInfomation(HttpServletRequest httpServletRequest) {
		AccountsDTO accountsDTO = new AccountsDTO();
			
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus() == "FALSE") {
			accountsDTO.setStatus(statusDTO);
			return accountsDTO;
		}
		
		String username = jwtService.getUsernameFromToken(authToken);
		Accounts accounts = accountsRepository.findByUsernameLike(username);
		accountsDTO = new AccountsDTO(accounts);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		accountsDTO.setStatus(statusDTO);
		return accountsDTO;
	}
	
	//Update accounts infomation
	@CrossOrigin(origins = "*")
	@PostMapping(API.UPDATEINFO)
	@ResponseBody
	public StatusDTO updateAccountInformation(@RequestBody AccountsDTO accountsDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE") return statusDTO;
		
		String username = jwtService.getUsernameFromToken(authToken);
		accountsRepositoryCustomImpl.update(username, accountsDTO);
		
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}
	
	//UPDATE Account password 
	@CrossOrigin(origins = "*")
	@PostMapping("/asdasdsad")
	@ResponseBody
	public StatusDTO updateAccountPassword(@RequestBody VeriPasswordDTO veriPasswordDTO) {
		
		StatusDTO statusDTO = new StatusDTO();
		Verification verification = verificationRepositoryCustomImpl.findCodeType(veriPasswordDTO.getVerificationCode(), "NEWPASSWORD");

		if (verification == null) {
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		Accounts accounts = accountsRepository.findById(verification.getAccountsId());
		

		//check username
		if ((veriPasswordDTO.getUsername() != null && !veriPasswordDTO.getUsername().isEmpty()) && !accounts.getUsername().equals(veriPasswordDTO.getUsername())){
			statusDTO.setStatus("FALSE");
			statusDTO.setMessage("Wrong Username");
			return statusDTO;
		}
		
		//check email
		if ((veriPasswordDTO.getEmail() != null && !veriPasswordDTO.getEmail().isEmpty()) && !accounts.getEmail().equals(veriPasswordDTO.getEmail())){
			statusDTO.setStatus("FALSE");
			statusDTO.setMessage("Wrong Email");
			return statusDTO;
		}
		
		accounts.setPassword(hashPassword.hash(veriPasswordDTO.getPassword()));
		verification.setActive("FALSE");
		accountsRepository.save(accounts);
		verificationRepository.save(verification);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}
	
	//Update Password
	@CrossOrigin(origins = "*")
	@PostMapping(API.UPDATEPASSWORD)
	@ResponseBody
	public StatusDTO resetPassword(@RequestBody PasswordDTO passwordDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE") return statusDTO;
		
		String username = jwtService.getUsernameFromToken(authToken);
		Accounts accounts = accountsRepository.findByUsernameLike(username);
		
		//check password
		if(!hashPassword.hash(passwordDTO.getPassword()).equals(accounts.getPassword())) {
			statusDTO.setMessage("Wrong password!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		accounts.setPassword(hashPassword.hash(passwordDTO.getNewPassword()));
		accountsRepository.save(accounts);
		statusDTO.setMessage("Successful");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}
	
	//verify Token
	@CrossOrigin(origins = "*")
	@PostMapping(API.VERIFYTOKEN)
	@ResponseBody
	public VerifyTokenDTO vetifyToken(@RequestBody TokenDTO tokenDTO) {
		StatusDTO statusDTO = new StatusDTO();
		VerifyTokenDTO verifyToken = new VerifyTokenDTO();
		statusDTO = checkJwt.checkJWT(tokenDTO.getToken(), false);
		verifyToken.setStatus(statusDTO);

		if (statusDTO.getStatus() == "FALSE") {
			return verifyToken;
		}
		
		String username = jwtService.getUsernameFromToken(tokenDTO.getToken());
		Accounts accounts = accountsRepository.findByUsernameLike(username);
		verifyToken.setPermission(accounts.getPermission());
		return verifyToken;
	}
}
