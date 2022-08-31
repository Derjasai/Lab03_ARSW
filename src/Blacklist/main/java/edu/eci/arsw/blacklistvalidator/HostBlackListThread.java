package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.lang.*;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class HostBlackListThread extends Thread{

    private HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();


    private int ini;
    private int fin;
    private String ipaddress;
    private LinkedList<Integer> blackListOcurrences = new LinkedList<>();

    private AtomicInteger checkedLists = new AtomicInteger(0);
    private AtomicInteger ocurrencesCount = new AtomicInteger(0);

    public HostBlackListThread(int ini, int fin, String ipaddress, AtomicInteger checkedLists, AtomicInteger ocurrencesCount){
        this.ini = ini;
        this.fin = fin;
        this.ipaddress = ipaddress;
        this.checkedLists = checkedLists;
        this.ocurrencesCount = ocurrencesCount;
    }

    public void run(){
        for (int i = ini; i < fin && ocurrencesCount.get() < HostBlackListsValidator.BLACK_LIST_ALARM_COUNT; i++){
            checkedLists.incrementAndGet();
            if(skds.isInBlackListServer(i, ipaddress)){
                addOnList(i);
                ocurrencesCount.incrementAndGet();
            }
        }
    }

    public synchronized int getOcurrencesCount(){ return ocurrencesCount.get(); }

    public synchronized LinkedList<Integer> getBlackListOcurrence(){
        return  blackListOcurrences;
    }

    public synchronized void addOnList(int i){
        blackListOcurrences.add(i);
    }

    public synchronized int getCheckedLists(){
        return checkedLists.get();
    }

}
