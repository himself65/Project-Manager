import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication(scanBasePackages = {"com.splask"})
public class Splask {


    public static void main(String[] args)
    {
        SpringApplication.run(Splask.class, args);
    }

}
