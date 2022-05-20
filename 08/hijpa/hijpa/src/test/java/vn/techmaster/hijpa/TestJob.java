package vn.techmaster.hijpa;

import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import vn.techmaster.hijpa.model.Job;

@DataJpaTest
public class TestJob {
    @Autowired private EntityManager em;
    @Test
    public void addJob(){
        Job job = Job.builder()
        .title("Java Developer")
        .description("Kinh nghiệm 12 tháng")
        .build();
        em.persist(job);
        System.out.println(job.getId());
        assertThat(job.getId()).isNotNull();
    }
}
