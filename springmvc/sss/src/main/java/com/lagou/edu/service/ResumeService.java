package com.lagou.edu.service;

import com.lagou.edu.pojo.Resume;

import java.util.List;

public interface ResumeService {

    List<Resume> queryAll();

    void deleteById(String id);

    void add(Resume resume);

    void update(Resume resume);

    Resume queryOne(String id);

}
