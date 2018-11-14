package ch.heigvd.amt.wp1.services;


import javax.ejb.Local;

@Local
public interface CheckTransactionsLocal {

    void start();
}
