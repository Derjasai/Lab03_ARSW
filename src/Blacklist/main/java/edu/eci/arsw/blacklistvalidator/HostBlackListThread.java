package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.lang.*;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class HostBlackListThread extends Thread{

    private HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();


    private int ini;
    private int fin;
    private String ipaddress;
    private CopyOnWriteArrayList<Integer> blackListOcurrences = new CopyOnWriteArrayList<>();

    private AtomicInteger checkedLists = new AtomicInteger(0);
    private AtomicInteger ocurrencesCount = new AtomicInteger(0);

    public HostBlackListThread(int ini, int fin, String ipaddress){
        this.ini = ini;
        this.fin = fin;
        this.ipaddress = ipaddress;
    }

    public void run(){
        for (int i = ini; i < fin && ocurrencesCount.get() < HostBlackListsValidator.BLACK_LIST_ALARM_COUNT; i++){
            checkedLists.addAndGet(1);
            if(skds.isInBlackListServer(i, ipaddress)){
                blackListOcurrences.add(i);
                ocurrencesCount.addAndGet(1);
            }
        }
    }

    public synchronized int getOcurrencesCount(){ return ocurrencesCount.get(); }

    public CopyOnWriteArrayList<Integer> getBlackListOcurrence(){
        return  blackListOcurrences;
    }

    public synchronized int getCheckedLists(){
        return checkedLists.get();
    }

}
