package com.ficheralezzi.fantasygo.Utils;

import org.json.JSONObject;

/**
 * Created by juan on 20/10/17.
 */

public class Messaggio extends JSONObject {

    private int messaggio; // Il Messaggio è codificato in numeri: Ex. 100 = OK
    private Object object;

    public Messaggio() {}

    public Messaggio(Integer messaggio, Object object) {
        this.messaggio = messaggio;
        this.object = object;
    }

    public int getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(int messaggio) {
        this.messaggio = messaggio;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Messaggio{" +
                "messaggio=" + messaggio +
                ", object=" + object +
                '}';
    }
}
