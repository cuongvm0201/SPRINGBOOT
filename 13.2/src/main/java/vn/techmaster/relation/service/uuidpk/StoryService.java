package vn.techmaster.relation.service.uuidpk;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.relation.model.uuidpk.Story;
import vn.techmaster.relation.repository.uuidpk.StoryRepo;

@Service
public class StoryService {
  @Autowired private StoryRepo storyRepo;
  
  @Transactional
  public void generateStory() {
    Story s1 = new Story("Hello World");
    storyRepo.save(s1);
    storyRepo.flush();
  }
}
