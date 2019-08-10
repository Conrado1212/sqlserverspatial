package slartidan.sqlserverspatial;

import org.locationtech.jts.geom.Geometry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@SpringBootApplication
public class SqlserverspatialApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlserverspatialApplication.class, args);
    }

}

@Entity
class MyEntity {

    @Id
    public String uuid;

    @Column(columnDefinition = "geography")
    public Geometry point;
}

interface MyRepository extends JpaRepository<MyEntity, String> {
}

@RestController
class MyController {
    private final MyRepository myRepository;

    MyController(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public MyEntity findByUuid(@PathVariable("uuid") String uuid) {
        return myRepository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
