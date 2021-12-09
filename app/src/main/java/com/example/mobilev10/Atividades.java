package com.example.mobilev10;

public class Atividades {
    private int _id;
    private String _desc;
    private String _dtInicio;

    private String _dtFim;
    private String _serv;
    private String _func;
    private String _cnpj;



    public Atividades() { }

    public Atividades(String desc,  String dtInicio, String dtFim,
                      String func, String cnpj, String serv){
        this.set_desc(desc);
        this.set_dtInicio(dtInicio);
        this.set_dtFim(dtFim);
        this.set_func(func);
        this.set_cnpj(cnpj);
        this.set_serv(serv);
    }

    public Atividades(int _id, String _desc, String _dtInicio, String _dtFim,
                      String _func, String _cnpj, String _serv) {
        this._id = _id;
        this._desc = _desc;
        this._dtInicio = _dtInicio;
        this._dtFim = _dtFim;
        this._func = _func;
        this._cnpj = _cnpj;
        this._serv = _serv;
    }

    public int get_id() { return _id; }
    public void set_id(int _id) { this._id = _id; }

    public String get_desc() { return _desc; }
    public void set_desc(String _desc) { this._desc = _desc; }


    public String get_dtInicio() { return _dtInicio; }
    public void set_dtInicio(String _dtInicio) { this._dtInicio = _dtInicio; }

    public String get_dtFim() { return _dtFim; }
    public void set_dtFim(String _dtFim) { this._dtFim = _dtFim; }

    public String get_func() { return _func; }
    public void set_func(String _func) { this._func = _func; }

    public String get_cnpj() { return _cnpj; }
    public void set_cnpj(String _cnpj) { this._cnpj = _cnpj; }

    public String get_serv() { return _serv; }
    public void set_serv(String _serv) { this._serv = _serv; }

}
