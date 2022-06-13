package vn.techmaster.relation.repository.manymany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.relation.model.manymany.separate_primary_key.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>{
  
}
