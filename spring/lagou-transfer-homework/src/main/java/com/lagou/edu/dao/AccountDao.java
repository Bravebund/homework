package com.lagou.edu.dao;

import com.lagou.edu.pojo.Account;
import com.lagou.edu.stereotype.Repository;

/**
 * @author 应癫
 */
//@Repository
public interface AccountDao {

    Account queryAccountByCardNo(String cardNo) throws Exception;

    int updateAccountByCardNo(Account account) throws Exception;
}
