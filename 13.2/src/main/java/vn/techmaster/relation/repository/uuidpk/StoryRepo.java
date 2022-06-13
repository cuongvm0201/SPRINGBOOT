package vn.techmaster.relation.repository.uuidpk;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.relation.model.uuidpk.Story;

@Repository
public interface StoryRepo extends JpaRepository<Story, UUID> {
  
}
