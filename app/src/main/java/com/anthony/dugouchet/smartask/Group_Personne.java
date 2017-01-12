package com.anthony.dugouchet.smartask;

/**
 * Created by dugou on 28/11/2016.
 */

public class Group_Personne {

    private int id_personne;
    private int id_group;

    public Group_Personne(int id_personne, int id_group) {
        this.id_personne = id_personne;
        this.id_group = id_group;
    }

    public int getId_personne() {

        return id_personne;
    }

    public void setId_personne(int id_personne) {
        this.id_personne = id_personne;
    }

    public int getId_group() {
        return id_group;
    }

    public void setId_group(int id_group) {
        this.id_group = id_group;
    }
}
