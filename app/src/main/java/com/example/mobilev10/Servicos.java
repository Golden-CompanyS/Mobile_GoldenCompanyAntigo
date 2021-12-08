package com.example.mobilev10;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Servicos implements Serializable {

    private int _id;
    private String _desc;

    public Servicos(){ }

    public Servicos(String desc){
        this.set_desc(desc);
    }

    public Servicos(int _id, String _desc) {
        this._id = _id;
        this._desc = _desc;
    }

    public int get_id() { return _id; }
    public void set_id(int _id) { this._id = _id; }

    public String get_desc() { return _desc; }
    public void set_desc(String _desc) { this._desc = _desc; }
}
