package com.avishaneu.testtasks.tls.dao.implementation;


import com.avishaneu.testtasks.tls.dao.RouteDao;
import com.avishaneu.testtasks.tls.model.Location;
import com.avishaneu.testtasks.tls.model.Route;
import com.avishaneu.testtasks.tls.utils.NonexistentObjectRequested;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.avishaneu.testtasks.tls.utils.DBUtils.setIntegerOrNull;
import static com.avishaneu.testtasks.tls.utils.DBUtils.setStringOrNull;

/**
 * Created by avishaneu on 7/25/17.
 */
@Repository
public class RouteDaoH2 implements RouteDao {

    private static final Logger log = LoggerFactory.getLogger(RouteDaoH2.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RouteDaoH2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Transactional
    public Route createRoute(Route route) {
        String sql = "INSERT INTO route (name, head) VALUES (?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, route.getName());
            ps.setDouble(2, route.getHead());
            return ps;
        }, holder);

        route.setId(holder.getKey().intValue());
        setLocationsToRoute(route.getId(), route.getLocations());

        return route;
    }

    @Transactional
    private void setLocationsToRoute(Integer routeID, List<Integer> locations) {
        String deleteSql = "DELETE FROM route_location WHERE route_id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(deleteSql);
            ps.setInt(1, routeID);
            return ps;
        });

        String createSql = "INSERT INTO route_location (route_id, location_id) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(createSql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Integer locationId = locations.get(i);
                ps.setInt(1, routeID);
                ps.setInt(2, locationId);
            }

            @Override
            public int getBatchSize() {
                return locations.size();
            }
        });
    }

    public Route getRoute(Integer id) {
        String routeSql = "SELECT * FROM route WHERE id = ?";
        String routeLocationsSql = "SELECT location_id FROM route_location WHERE route_id = ?";
        try {
            Route route = jdbcTemplate.queryForObject(routeSql, new Object[]{id},
                    new BeanPropertyRowMapper<>(Route.class));
            route.setLocations(jdbcTemplate.query(routeLocationsSql, new Object[]{id},
                    (rs, rowNum) -> rs.getInt(1)));
            return route;
        } catch (EmptyResultDataAccessException e) {
            throw new NonexistentObjectRequested("route", id, e);
        }
    }

    @Transactional
    public void updateRoute(Route route) {
        StringBuilder updateRoute = new StringBuilder();
        updateRoute.append("UPDATE ROUTE")
                .append(" SET name = IFNULL(?, name),")
                .append(" head = IFNULL(?, head)")
                .append(" WHERE id = ?");
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(updateRoute.toString());
            setStringOrNull(ps, 1, route.getName());
            setIntegerOrNull(ps, 2, route.getHead());
            ps.setInt(3, route.getId());
            return ps;
        });

        if (route.getLocations() == null || route.getLocations().isEmpty()) return;
        setLocationsToRoute(route.getId(), route.getLocations());
    }

    public void deleteRoute(Integer id) {
        String sql = "DELETE route  WHERE id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            return ps;
        });
    }

    public List<Integer> getRoutePlan(Integer id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT location_id from")
                .append(" (SELECT location_id, location_order")
                .append(" FROM route_location ")
                .append(" WHERE route_id = ?")
                .append(" UNION")
                .append(" SELECT head, 1")
                .append(" FROM route")
                .append(" WHERE id = ?)")
                .append(" ORDER BY location_order ASC");
        try {
            return jdbcTemplate.query(sql.toString(), new Object[]{id, id},
                    (rs, rowNum) -> rs.getInt(1));
        } catch (EmptyResultDataAccessException e) {
            throw new NonexistentObjectRequested("route", id, e);
        }
    }

    public List<Location> getRouteLocations(Integer id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT loc.id, head, loc.x, loc.y")
                .append(" FROM location loc")
                .append(" JOIN (SELECT location_id, 0 AS head")
                .append(" FROM route_location")
                .append(" WHERE route_id = ?")
                .append(" UNION")
                .append(" SELECT head, 1")
                .append(" FROM route")
                .append(" WHERE route.id = ?) t")
                .append(" ON loc.id = t.location_id");
        try {
            return jdbcTemplate.query(sql.toString(), new Object[]{id, id},
                    new BeanPropertyRowMapper<>(Location.class));
        } catch (EmptyResultDataAccessException e) {
            throw new NonexistentObjectRequested("route", id, e);
        }
    }

    public void saveRoutePlan(Integer id, List<Location> locations) {
        String createSql = "UPDATE route_location SET location_order = ? WHERE route_id = ? and location_id = ?";
        jdbcTemplate.batchUpdate(createSql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Location location = locations.get(i);
                ps.setInt(1, i);
                ps.setInt(2, id);
                ps.setInt(3, location.getId());
            }

            @Override
            public int getBatchSize() {
                return locations.size();
            }
        });
    }
}
