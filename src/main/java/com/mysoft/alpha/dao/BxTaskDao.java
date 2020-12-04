package com.mysoft.alpha.dao;

import com.mysoft.alpha.entity.BxTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * (BxTask)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-29 14:13:01
 */
public interface BxTaskDao extends JpaRepository<BxTask, Integer> {

    @Query(value = "SELECT SUM(amount) FROM bx.bx_task  WHERE user_id = :userId ",
           nativeQuery = true)
    Long findAmountByParamsAndSort(@Param(value = "userId") Integer userId);


}