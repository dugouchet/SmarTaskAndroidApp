package com.anthony.dugouchet.smartask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dugou on 28/11/2016.
 */

public class Group_Personne_Manager {

    public static final String TABLE_GROUP_PERSONNE= "group_personne_List";
    public static final String KEY_ID_GROUP_GP="id_group_gp";
    public static final String KEY_ID_PERSON_GP="id_personne_gp";

    public static final String CREATE_TABLE_GROUP_PERSONNE = "CREATE TABLE IF NOT EXISTS "+TABLE_GROUP_PERSONNE+
            " (" +
            " "+KEY_ID_GROUP_GP+" INTEGER," +
            " "+KEY_ID_PERSON_GP+" INTEGER" +
            ");";
    private MySQLite maBaseSQLite; //  gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public Group_Personne_Manager (Context context)
    {
        //maBaseSQLite = MySQLite.getInstance(context,CREATE_TABLE_GROUP);
        //maBaseSQLite = new MySQLite(context,CREATE_TABLE_GROUP_PERSONNE);
    }

    public void open()
    {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addGroup_Personne(Group_Personne group_Personne) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_GROUP_GP, group_Personne.getId_group());
        values.put(KEY_ID_PERSON_GP, group_Personne.getId_personne());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_GROUP_PERSONNE,null,values);
    }

    public int modifyGroup(Group_Personne group_Personne) {
return 1;
    }

    public int deleteGroup_by_personn(Group_Personne group) {
return 1;
    }

    public Group_Personne getGroup_Personne_by_group(int id_group) {
        // Retourne la tache dont l'id est passé en paramètre

        Group_Personne a=new Group_Personne(0,0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_GROUP_PERSONNE+" WHERE "+KEY_ID_GROUP_GP+"="+id_group, null);
        if (c.moveToFirst()) {
            a.setId_group(c.getInt(c.getColumnIndex(KEY_ID_GROUP_GP)));
            a.setId_personne(c.getInt(c.getColumnIndex(KEY_ID_PERSON_GP)));
            c.close();
        }
        return a;
    }

    public Group_Personne getGroup_Personne_by_person(int id_person) {
        // Retourne la tache dont l'id est passé en paramètre

        Group_Personne a=new Group_Personne(0,0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_GROUP_PERSONNE+" WHERE "+KEY_ID_PERSON_GP+"="+id_person, null);
        if (c.moveToFirst()) {
            a.setId_group(c.getInt(c.getColumnIndex(KEY_ID_GROUP_GP)));
            a.setId_personne(c.getInt(c.getColumnIndex(KEY_ID_PERSON_GP)));
            c.close();
        }
        return a;
    }

    public int getNumberOfGroup(){

        return db.rawQuery("SELECT * FROM " + TABLE_GROUP_PERSONNE, null).getCount();
    }

    public Cursor getAllGroup(){

        return db.rawQuery("SELECT * FROM " + TABLE_GROUP_PERSONNE, null);
    }

}

