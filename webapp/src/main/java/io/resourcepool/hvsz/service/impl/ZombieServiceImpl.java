package io.resourcepool.hvsz.service.impl;

import io.resourcepool.hvsz.persistance.dao.DaoMapDb;
import io.resourcepool.hvsz.persistance.models.Game;
import io.resourcepool.hvsz.persistance.models.GameStatus;
import io.resourcepool.hvsz.persistance.models.Life;
import io.resourcepool.hvsz.service.ZombieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZombieServiceImpl implements ZombieService {
    @Autowired
    private DaoMapDb dao;

    @Override
    public boolean kill(String lifeToken) {
        Game g = dao.get(1L);

        GameStatus status = g.getStatus();

            Boolean lifeFound = false;
            //Decrement nbHumaneAlive in game status
            status.setNbHumanAlive(status.getNbHumanAlive() - 1);
            for (Life l : status.getLives()) {
                if (l.getToken().toString().equals(lifeToken)) {
                    l.setAlive(false);
                    lifeFound = true;
                }
            }
            g.setStatus(status);
            dao.set(1L, g);
            return lifeFound;

    }
}