package testSecurity;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {
    @Test
    public void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode1 = passwordEncoder.encode("102785");
        String encode2 = passwordEncoder.encode("102785");
        System.out.println(encode1);
        System.out.println(encode2);
//        $2a$10$NokXnum/k1NAewIibnLM6ex5y0aDkdkjhbwN/HkDLlIV/FFMFNRiS
    }
}
