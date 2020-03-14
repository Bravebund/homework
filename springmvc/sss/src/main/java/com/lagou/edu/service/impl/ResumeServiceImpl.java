package com.lagou.edu.service.impl;

import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeDao resumeDao;

    @Override
    public List<Resume> queryAll() {
        List<Resume> all = resumeDao.findAll();
        return all;
    }

    @Override
    public Resume queryOne(String id) {
        Optional<Resume> optional = resumeDao.findById(Long.parseLong(id));
        return optional.get();
    }

    @Override
    public void deleteById(String id) {
        resumeDao.deleteById(Long.parseLong(id));
    }

    @Override
    public void add(Resume resume) {
        //Resume save = resumeDao.save(resume);
        Resume save = resumeDao.save(resume);
        System.out.println(save);
    }

    @Override
    public void update(Resume resume) {
        Resume update = resumeDao.save(resume);
        System.out.println(update);
    }


}
