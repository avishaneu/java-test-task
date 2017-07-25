package com.avishaneu.testtasks.tls.dao.implementation;

import com.avishaneu.testtasks.tls.dao.LocationDao;
import com.avishaneu.testtasks.tls.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import static com.avishaneu.testtasks.tls.utils.DBUtils.setDoubleOrNull;
import static com.avishaneu.testtasks.tls.utils.DBUtils.setStringOrNull;


/**
 * Created by avishaneu on 7/25/17.
 */
@Repository
public class LocationDaoH2 implements LocationDao {

    private static final Logger log = LoggerFactory.getLogger(LocationDaoH2.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public LocationDaoH2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Location createLocation(Location location) {
        String sql = "INSERT INTO location (name, x, y) VALUES (?, ?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, location.getName());
            ps.setDouble(2, location.getX());
            ps.setDouble(3, location.getY());
            return ps;
        }, holder);
        location.setId(holder.getKey().intValue());
        return location;
    }

    public Location getLocation(Integer id) {
        String sql = "SELECT * FROM location WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Location.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void updateLocation(Location location) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE location")
                .append(" SET name = IFNULL(?, name),")
                .append(" x = IFNULL(?, x),")
                .append(" y = IFNULL(?, y) ")
                .append(" WHERE id = ?");
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql.toString());
            setStringOrNull(ps, 1, location.getName());
            setDoubleOrNull(ps, 2, location.getX());
            setDoubleOrNull(ps, 3, location.getY());
            ps.setInt(4, location.getId());
            return ps;
        });
    }

    public void deleteLocation(Integer id) {
        String sql = "DELETE location  WHERE id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            return ps;
        });
    }

    public List<Integer> getLocationRoutes(Integer id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT route_id")
                .append(" FROM route_location ")
                .append(" WHERE location_id = ?")
                .append(" UNION")
                .append(" SELECT id")
                .append(" FROM route")
                .append(" WHERE head = ?");
        try {
            return jdbcTemplate.query(sql.toString(), new Object[]{id, id},
                    (rs, rowNum) -> rs.getInt(1));
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }
}
