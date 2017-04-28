package io.resourcepool.hvsz.persistance.models;

import java.io.Serializable;

/**
 * Created by ebiz on 28/04/17.
 */
public class Life implements Serializable {

    private Integer id;
    private boolean alive;
    private int nbResources;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getNbResources() {
        return this.nbResources;
    }

    public void setNbResources(int nbResources) {
        this.nbResources = nbResources;
    }


    /**
     * drop res.
     * @param qte qte to drop
     * @return dropped qte
     */
    public int dropResources(int qte) {
        if (nbResources - qte >= 0) {
            this.nbResources -= qte;
            return qte;
        } else {
            int removed = this.nbResources;
            this.nbResources = 0;
            return removed;
        }
    }

    /**
     * .
     * @param qte .
     */
    public void addResource(int qte) {
        this.nbResources += qte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Life life = (Life) o;

        return getId().equals(life.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}