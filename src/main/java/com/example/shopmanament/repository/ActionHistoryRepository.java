package com.example.shopmanament.repository;

import com.example.shopmanament.entity.ActionHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionHistoryRepository extends JpaRepository<ActionHistory, Long> {
    @Query("select e from ActionHistory e where e.userId = :userId order by e.createAt desc limit 20")
    List<ActionHistory> getByUser (Long userId);
    @Query("select e.createAt, MIN (CASE WHEN e.message like '%đăng nhập%' THEN e.createAt END) AS first_login, " +
            "MAX(CASE WHEN e.message = '%đăng xuất%' THEN e.createAt END) AS last_logout " +
            "from ActionHistory e where (STR_TO_DATE(e.createAt, '%Y-%m-%d') = STR_TO_DATE(:time, '%Y-%m-%d')) " +
            "and e.userId = :userId " +
            "group by e.userId"
    )
    List<Object[]> getWorkPoint(@Param("time") String time, @Param("userId") Long userId);

}
