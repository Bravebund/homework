import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.ResumeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dao.xml",
        "classpath:applicationContext-service.xml"})
public class ResumeServiceTest {

    @Autowired
    private ResumeService resumeService;

    @Test
    public void testFindAll() {

        List<Resume> resumeList = resumeService.queryAll();
        for (Resume resume : resumeList) {
            System.out.println(resume.toString());
        }

    }

    @Test
    public void testAdd() {

        Resume resume = new Resume();
        resume.setId(4L);
        resume.setName("等等");
        resume.setPhone("13612345678");
        resume.setAddress("深圳");

        resumeService.add(resume);

    }
}
