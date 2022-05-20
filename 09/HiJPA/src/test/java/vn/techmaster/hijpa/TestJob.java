package vn.techmaster.hijpa;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import vn.techmaster.hijpa.model.Job;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class TestJob {
  @Autowired private EntityManager em;

  @Test
  public void addJob() {
    Job job = Job.builder()
    .title("Java developer")
    .description("Kinh nghiệm 12 tháng")
    .build();

    em.persist(job);
    assertThat(job.getId()).isNotNull();
    assertThat(job.getId().toString()).hasSizeGreaterThan(10);
  }

}
