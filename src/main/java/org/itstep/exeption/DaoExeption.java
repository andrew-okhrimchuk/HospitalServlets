package org.itstep.exeption;


public class DaoExeption extends Exception  {
    private  Exception e;

    public DaoExeption(String msg, Exception e) {
        super(msg);
        this.e = e;
    }
}
