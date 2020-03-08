package com.lagou.edu.service;

import com.lagou.edu.stereotype.Service;
import com.lagou.edu.stereotype.Transactional;

/**
 * @author 应癫
 */
//@Service("transferService")
//@Transactional
public interface TransferService {

    void transfer(String fromCardNo, String toCardNo, int money) throws Exception;
}
