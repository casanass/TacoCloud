package com.example.tacocloud.repository.impl;

import com.example.tacocloud.model.Ingredient;
import com.example.tacocloud.model.Taco;
import com.example.tacocloud.repository.TacoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class TacoRepositoryImpl implements TacoRepository {

    private static final Logger logger = LoggerFactory.getLogger(TacoRepositoryImpl.class);

    private JdbcTemplate jdbc;

    @Autowired
    public TacoRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        logger.info("To save tacoId: {} with {} ingredients", tacoId, taco.getIngredients().size());

        for(Ingredient ingredient: taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        logger.info("Saved tacoId:{} ingredients", tacoId);
        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        String sql = "insert into tacos(name, createDt) values(?, ?)";
        taco.setCreateDt(new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                    sql, Types.VARCHAR, Types.TIMESTAMP);
            pscf.setReturnGeneratedKeys(true);
            PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                    Arrays.asList(taco.getName(), new Timestamp(taco.getCreateDt().getTime())));

            jdbc.update(psc, keyHolder);
        } catch (Exception e) {
            logger.error("Error in saveTacoInfo ", e);
        }
        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        try {
            String sql = "insert into taco_ingredients (tacoId, ingreId) values (?, ?)";
            jdbc.update(sql, tacoId, ingredient.getId());
        } catch (Exception e) {
            logger.error("Error in saveIngredientToTaco ", e);
        }
    }
}
