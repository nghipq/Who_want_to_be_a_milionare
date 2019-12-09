/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

/**
 *
 * @author phamq
 */
public class AnswerException extends Exception{
    /**
     * create new AnswerException
     * @param mesage 
     */
    public AnswerException (String mesage) {
        super(mesage);
    }

    @Override
    public String toString() {
        return "AnswerException: " + this.getMessage();
    }
}
