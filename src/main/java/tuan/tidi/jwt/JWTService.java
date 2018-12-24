package tuan.tidi.jwt;
import java.util.Date;
import org.springframework.stereotype.Service;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
@Service
public class JWTService {
	public static final String USERNAME = "username";
	public static final String PERMISSION = "permission";
	public static final String SECRET_KEY = "n1g1u1y1e1n1m1i1n1h1t1u1a1n11111";
	public static final int EXPIRE_TIME = 86400000;
  
	public String generateTokenLogin(String username, String permission) {
		String token = null;
		try {
			
			JWSSigner signer = new MACSigner(generateShareSecret());
			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
			builder.claim(USERNAME, username);
			builder.claim(PERMISSION, permission);
			builder.claim("role", "USER");
			builder.expirationTime(generateExpirationDate());
			JWTClaimsSet claimsSet = builder.build();
			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
			
			signedJWT.sign(signer);
			
			token = signedJWT.serialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
  
	private JWTClaimsSet getClaimsFromToken(String token) {
		JWTClaimsSet claims = null;
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWSVerifier verifier = new MACVerifier(generateShareSecret());
			if (signedJWT.verify(verifier)) {
				claims = signedJWT.getJWTClaimsSet();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return claims;
	}
  
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + EXPIRE_TIME);
	}
  
	private Date getExpirationDateFromToken(String token) {
		Date expiration = null;
		JWTClaimsSet claims = getClaimsFromToken(token);
		expiration = claims.getExpirationTime();
		return expiration;
	}
	
	public String getUsernameFromToken(String token) {
		String username = null;
		try {
			JWTClaimsSet claims = getClaimsFromToken(token);
			username = claims.getStringClaim(USERNAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return username;
	}
	
	public String getPermission(String token) {
		String permission = null;
		try {
			JWTClaimsSet claims = getClaimsFromToken(token);
			permission = claims.getStringClaim(PERMISSION);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return permission;
	}
	
	private byte[] generateShareSecret() {
		// Generate 256-bit (32-byte) shared secret
		byte[] sharedSecret = new byte[32];
		sharedSecret = SECRET_KEY.getBytes();
		return sharedSecret;
	}
	
	public Boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public Boolean validateTokenLogin(String token) {
		if (token == null || token.trim().length() == 0) {
			return false;
		}
		String username = getUsernameFromToken(token);
		if (username == null || username.isEmpty()) {
			return false;
		}
		return true;
	}
}