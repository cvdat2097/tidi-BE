package tuan.tidi.service;
import java.util.Random;
public class RandomVerificationCode {
	
	public String randomCode() {
		String s = "";
		Random rand = new Random();
		for (int i = 0; i < 8; i++) {
			int n = rand.nextInt(26) + 65;
			s = s + (char) n;
		}
		return s;
	}
}
