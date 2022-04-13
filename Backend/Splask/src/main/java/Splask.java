import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.splask"})
@ComponentScan({"com.splask"})
@EntityScan("com.splask.Models")
@EnableJpaRepositories("com.splask.Repositories")
@OpenAPIDefinition
public class Splask {


    public static void main(String[] args)
    {
        SpringApplication.run(Splask.class, args);
    }

}
