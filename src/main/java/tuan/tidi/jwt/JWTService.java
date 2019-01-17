package tuan.tidi.jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Service
public class JWTService {
	public static final String USERNAME = "username";
	public static final String PERMISSION = "permission";
	public static final String SECRET_KEY = "n1g1u1y1e1n1m1i1n1h1t1u1a1n11111";
	public static final int EXPIRE_TIME = 3600000;
  
//	public String generateTokenLogin(String username, String permission) {
//		String token = null;
//		try {
//			
//			JWSSigner signer = new MACSigner(generateShareSecret());
//			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
//			builder.claim(USERNAME, username);
//			builder.claim(PERMISSION, permission);
//			builder.claim("scopes", "USER");
//			builder.expirationTime(generateExpirationDate());
//			JWTClaimsSet claimsSet = builder.build();
//			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
//			
//			signedJWT.sign(signer);
//			
//			token = signedJWT.serialize();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return token;
//	}
////  
//	
	public String generateTokenLogin(String username, String permission) {

		return Jwts.builder()
	            .setSubject(username)
	            .claim("scopes", "ROLE_" + permission)
	            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_TIME))
	            .compact();
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
  
//	private Date getExpirationDateFromToken(String token) {
//		Date expiration = null;
//		JWTClaimsSet claims = getClaimsFromToken(token);
//		expiration = claims.getExpirationTime();
//		return expiration;
//	}
	
//	public String getUsernameFromToken(String token) {
//		String username = null;
//		try {
//			JWTClaimsSet claims = getClaimsFromToken(token);
//			username = claims.getStringClaim(USERNAME);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return username;
//	}
	
	public String getPermission(String token) {
		String permission = null;
		final JwtParser jwtParser = Jwts.parser().setSigningKey(SECRET_KEY);

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();
        permission = claims.get("scopes").toString();
		return permission;
	}
	
	private byte[] generateShareSecret() {
		// Generate 256-bit (32-byte) shared secret
		byte[] sharedSecret = new byte[32];
		sharedSecret = SECRET_KEY.getBytes();
		return sharedSecret;
	}
	
//	public Boolean isTokenExpired(String token) {
//		Date expiration = getExpirationDateFromToken(token);
//		return expiration.before(new Date());
//	}
	
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
	
	public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
              username.equals(userDetails.getUsername())
                    && !isTokenExpired(token));
    }
	
	public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
	
	public UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth, final UserDetails userDetails) {

        final JwtParser jwtParser = Jwts.parser().setSigningKey(SECRET_KEY);

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("scopes").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }
}