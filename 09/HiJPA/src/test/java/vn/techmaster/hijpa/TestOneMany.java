package vn.techmaster.hijpa;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import vn.techmaster.hijpa.model.unidirection.Category;
import vn.techmaster.hijpa.model.unidirection.Product;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class TestOneMany {
  @Autowired private EntityManager em;
  @Test @Transactional
  public void testProductCategory() {
    Category homeappliance = new Category("Home Appliance");
    Product fridge = new Product("Fridge", homeappliance);
    //em.persist(homeappliance);
    em.persist(fridge);  
    assertThat(fridge.getCategory()).isEqualTo(homeappliance);
    em.flush();

    //var  homeappliance_id = homeappliance.getId();
    em.remove(fridge);
    em.flush();
  }
}
