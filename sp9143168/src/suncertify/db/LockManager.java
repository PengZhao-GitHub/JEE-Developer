package suncertify.db;

import java.util.*;

/**
 * This class provides lock management functions. The LockManager does not
 * create all the locks related to each row, but create row locks on demand.
 * And if there is no thread waiting on a row lock, the row lock will be deleted.
 *
 * @author Peng Zhao
 * @version 1.0  1-JUN-2002
 */
public class LockManager {
  //Maintain the row lock(Each row lock is an instance of Locker class).
  private Hashtable recordLockers = null;
  //Maintain the database lock.
  private Locker dbLocker = null;

  /**
    * This constructor creates a database locker and a Hashtable to hold records' locker.
    */
  public LockManager(){
      recordLockers = new Hashtable();
      dbLocker =  new Locker();
  }

  /**
    * Get the locker related to the record number. If record number is -1 then
    * get the database locker.
    *
    * @param int recordNumber The record to be locked.
    * @exception IOException Thrown if cannot lock the record.
    */
  public void getLocker(int recordNumber) throws Exception{
      if (recordNumber >= 0 && !dbLocker.isLocked()){
          // Record lock
          Locker lck = null;
          synchronized(this){
              Integer key = new Integer(recordNumber);
              lck = (Locker)(recordLockers.get(key));

              if (lck == null){
                  lck = new Locker();
                  recordLockers.put(key,lck);
              }
          }
          lck.lock();

      } else if (recordNumber == -1 && recordLockers.size() == 0){
          // Database lock
          dbLocker.lock();
      } else {
          // Lock exception
          throw new Exception("Wrong record number[" + recordNumber + "]");
      }
  }

  /**
    * Rlease the locker related to the record number.
    *
    * @param int recordNumber The record to be unlocked.
    */
  public void releaseLocker(int recordNumber){
      //Row lock
      if (recordNumber >= 0){
            // Release record locker
            Integer key = new Integer(recordNumber);
            Locker lck = (Locker)(recordLockers.get(key));

            if (lck == null){
                return;
            }
            lck.unLock();

            //If there is no thread waiting for this locker and
            //currently no thread locking this locker, then delete it.
            if (lck.isQueueEmpty() && !lck.isLocked()){
                recordLockers.remove(key);
            }
      //Database lock
      } else if (recordNumber == -1){
            // Release database locker
            dbLocker.unLock();
      }
  }
}

/**
 * This class implements a locker which can be used as record lock or db lock.
 *
 * @author Peng Zhao
 * @version 1.0  1-JUN-2002
 */
class Locker {
  // The thread which holds the locker currently. null indecates that there
  // is no thread holds the locker.
  private Thread occupyThread = null;
  // The queue which holds all the threads waiting for the locker.
  private Vector waitingQueue = new Vector();

  /**
    * Returns true if the waiting queue is empty, or false if threre are threads wating
    * for this locker.
    *
    * @return boolean true if waiting queue is empty, or false if otherwise.
    */
  public synchronized boolean isQueueEmpty(){
      if (waitingQueue.size()==0){
          return true;
      } else {
          return false;
      }
  }

  /**
    * Returns true if the locker has been locked, or false if otherwise.
    *
    * @return boolean
    */
  public synchronized boolean isLocked(){
      if (occupyThread != null){
          return true;
      } else {
          return false;
      }
  }

  /**
    * Lock this locker. If the locker has been locked then put the current thread
    * to the waiting queue and wait for being notified.
    */
  public synchronized void lock(){
      //Wait until the occupyThread release the locker
      while(occupyThread != null){
          waitingQueue.addElement(Thread.currentThread());
          try {
              wait();
          } catch(Exception ex){}
      }
      //Set the current thread as the occupyThread
      occupyThread = Thread.currentThread();

      //Remove the current thread from the waiting queue if it is there.
      if (waitingQueue.contains(occupyThread)){
          waitingQueue.remove(occupyThread);
      }
  }

  /**
    * Unlock this locker. If the locker belongs to the current thread, then release it.
    * Otherwise do nothing.
    */
  public synchronized void unLock(){
      Thread currentThread = Thread.currentThread();
      if (currentThread.equals(occupyThread)){
          occupyThread = null;
          notifyAll();
      }
  }
}