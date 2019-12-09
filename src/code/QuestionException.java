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
public class QuestionException extends Exception{
    /**
     * create new question exception
     * @param message 
     */
    public QuestionException (String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "QuestionException: " + this.getMessage();
    }
    
    
}
