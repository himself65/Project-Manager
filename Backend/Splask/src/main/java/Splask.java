import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication(scanBasePackages = {"com.splask.Services"})
@OpenAPIDefinition
public class Splask {


    public static void main(String[] args)
    {
        SpringApplication.run(Splask.class, args);
    }

}
