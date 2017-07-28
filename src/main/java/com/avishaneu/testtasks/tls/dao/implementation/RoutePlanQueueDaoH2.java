package com.avishaneu.testtasks.tls.dao.implementation;

import com.avishaneu.testtasks.tls.dao.RoutePlanQueueDao;
import com.avishaneu.testtasks.tls.model.RoutePlanGenerationStatus;
import com.avishaneu.testtasks.tls.utils.NonexistentObjectRequested;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

/**
 * Created by avishaneu on 7/25/17.
 */
@Component
public class RoutePlanQueueDaoH2 implements RoutePlanQueueDao {

    private static final Logger log = LoggerFactory.getLogger(RoutePlanQueueDaoH2.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoutePlanQueueDaoH2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RoutePlanGenerationStatus getRoutePlanStatusByRouteId(Integer routeId) {
        String sql = "SELECT state, queue_id FROM route_plan_generation_queue WHERE route_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{routeId},
                    (rs, rowNum) -> new RoutePlanGenerationStatus(routeId,
                            rs.getInt(1), rs.getInt(2)));
        } catch (EmptyResultDataAccessException e) {
            throw new NonexistentObjectRequested("route", routeId, e);
        }
    }

    public RoutePlanGenerationStatus getRoutePlanStatusByQueueId(Integer queueId) {
        String sql = "SELECT route_id, state FROM route_plan_generation_queue WHERE queue_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{queueId},
                    (rs, rowNum) -> new RoutePlanGenerationStatus(rs.getInt(1),
                            rs.getInt(2), queueId));
        } catch (EmptyResultDataAccessException e) {
            throw new NonexistentObjectRequested("queue element", queueId, e);
        }
    }

    public void addRouteToGenerationQueue(Integer routeId, Integer queueId) {
        String sql = "MERGE INTO route_plan_generation_queue(route_id, queue_id, state) " +
                "KEY(route_id) SELECT ?, ?, ? FROM DUAL";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, routeId);
            ps.setInt(2, queueId);
            ps.setInt(3, RoutePlanGenerationStatus.Status.PENDING.getCode());
            return ps;
        });
    }

    public void updateRouteGenerationStatus(Integer routeId, RoutePlanGenerationStatus.Status status) {
        String sql = "MERGE INTO route_plan_generation_queue(route_id, state) " +
                "KEY(route_id) SELECT ?, ? FROM DUAL";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, routeId);
            ps.setInt(2, status.getCode());
            return ps;
        });
    }
}
