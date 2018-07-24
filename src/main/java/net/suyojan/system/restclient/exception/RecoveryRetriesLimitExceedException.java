/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.exception;

/**
 *
 * @author Suyojan
 */
public class RecoveryRetriesLimitExceedException extends Exception
{
     public RecoveryRetriesLimitExceedException()
     {
         super("Number of Retries Exceeded the Default Value");
     }
    public RecoveryRetriesLimitExceedException(String message)
    {
        super(message);
    }
    
    public RecoveryRetriesLimitExceedException(int retriesLimit)
    {
        super(retriesLimit +" times tried but not succeeded");
    }
    
    public RecoveryRetriesLimitExceedException(String message , int retriesLimit)
    {
        super(message +" Tries Limit ="+retriesLimit);
    }
}
