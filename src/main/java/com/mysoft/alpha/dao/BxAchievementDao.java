package com.mysoft.alpha.dao;

import com.mysoft.alpha.entity.BxAchievement;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * (BxAchievement)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-29 14:13:12
 */
public interface BxAchievementDao extends JpaRepository<BxAchievement, Integer> {
	
    @Query(value = "SELECT \n"
    		+ "     CONCAT(DATE_FORMAT(MIN(data_time), '%Y-%m-%d'),\n"
    		+ "                    '至',\n"
    		+ "            DATE_FORMAT(MAX(data_time), '%Y-%m-%d')) period,\n"
    		+ "    IFNULL(SUM(insure_amount) + SUM(official_amount),\n"
    		+ "            0) amount,\n"
    		+ "    IFNULL(SUM(insure_premium) + SUM(official_premium),\n"
    		+ "            0) premium,\n"
    		+ "    IFNULL(SUM(insure_exposure_num) + SUM(official_follow_num),\n"
    		+ "            0) num\n"
    		+ "FROM\n"
    		+ "    bx.achieve_data\n"
    		+ "WHERE\n"
    		+ "    data_time >= DATE_FORMAT('2020-12-01', '%Y-%m-%d')\n"
    		+ "        AND data_time <= DATE_SUB(CURDATE(), INTERVAL 1 DAY)",
            nativeQuery = true)
	Map<String, Object> findSumData();
    
    @Query(value = "SELECT \n"
    		+ "    DATE_FORMAT(data_time, '%Y-%m-%d') period,\n"
    		+ "    IFNULL(SUM(insure_amount) + SUM(official_amount),\n"
    		+ "            0) amount,\n"
    		+ "    IFNULL(SUM(insure_premium) + SUM(official_premium),\n"
    		+ "            0) premium,\n"
    		+ "    IFNULL(SUM(insure_exposure_num) + SUM(official_follow_num),\n"
    		+ "            0) num\n"
    		+ "FROM\n"
    		+ "    bx.achieve_data\n"
    		+ "WHERE\n"
    		+ "    data_time >= DATE_FORMAT('2020-12-01', '%Y-%m-%d')\n"
    		+ "        AND data_time <= DATE_SUB(CURDATE(), INTERVAL 1 DAY)\n"
    		+ "GROUP BY DATE_FORMAT(data_time, '%Y-%m-%d')\n"
    		+ "ORDER BY DATE_FORMAT(data_time, '%Y-%m-%d')",
            nativeQuery = true)
    List<Map<String, Object>> findSumDataByDate();
    
    @Query(value = "select team_name dept,amount,premium,num from (     \n"
    		+ "SELECT \n"
    		+ "team_name ,min(team_order) team_order,\n"
    		+ "    IFNULL(SUM(insure_amount) + SUM(official_amount),\n"
    		+ "            0) amount,\n"
    		+ "    IFNULL(SUM(insure_premium) + SUM(official_premium),\n"
    		+ "            0) premium,\n"
    		+ "    IFNULL(SUM(insure_exposure_num) + SUM(official_follow_num),\n"
    		+ "            0) num\n"
    		+ "FROM\n"
    		+ "    bx.achieve_data\n"
    		+ "WHERE\n"
    		+ "    data_time >= DATE_FORMAT('2020-12-01', '%Y-%m-%d')\n"
    		+ "        AND data_time <= DATE_SUB(CURDATE(), INTERVAL 1 DAY)\n"
    		+ "GROUP BY team_name\n"
    		+ "ORDER BY amount desc,team_order asc\n"
    		+ ") as temp limit 10",
            nativeQuery = true)
    List<Map<String, Object>> findTop10DeptData();
    
    @Query(value = "select user_name name,amount ,premium ,num from (     \n"
    		+ "SELECT \n"
    		+ "user_name,team_name ,user_id,\n"
    		+ "    IFNULL(SUM(insure_amount) + SUM(official_amount),\n"
    		+ "            0) amount,\n"
    		+ "    IFNULL(SUM(insure_premium) + SUM(official_premium),\n"
    		+ "            0) premium,\n"
    		+ "    IFNULL(SUM(insure_exposure_num) + SUM(official_follow_num),\n"
    		+ "            0) num\n"
    		+ "FROM\n"
    		+ "    bx.achieve_data\n"
    		+ "WHERE\n"
    		+ "    data_time >= DATE_FORMAT('2020-12-01', '%Y-%m-%d')\n"
    		+ "        AND data_time <= DATE_SUB(CURDATE(), INTERVAL 1 DAY)\n"
    		+ "GROUP BY user_name,team_name ,user_id\n"
    		+ "ORDER BY amount desc,user_id asc\n"
    		+ ") as temp limit 10",
            nativeQuery = true)
    List<Map<String, Object>> findTop10PersonData();

    @Query(value = "SELECT SUM(amount) FROM bx.bx_achievement  WHERE promotion_id = :promotionId ",
           nativeQuery = true)
    Long findAmountByParamsAndSort(@Param(value = "promotionId") Integer promotionId);
    
    List<BxAchievement> findAllByPromotionId(@Param(value = "promotionId") Integer promotionId);

    @Query(value = "select * from bx_achievement achievement where achievement.promotion_id =:promotionId"	, 
            countQuery = "select count(*) from bx_achievement achievement where achievement.promotion_id =:promotionId",
            nativeQuery = true)
    Page<BxAchievement> findPageByPromotionId(@Param(value = "promotionId") Integer promotionId, Pageable pageable);
    
}