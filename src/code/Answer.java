package code;


import code.AnswerException;
import code.QuestionException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author phamq
 */
public class Answer {
    private int aId; //answer id
    private String aContent; //answer content
    private boolean aStatus; //answer status
    private boolean aSelected; //answer selected
    private int qId; //question id

    /**
     * get answer id
     * @return 
     */
    public int getaId() {
        return aId;
    }

    /**
     * set id for answer
     * @param aId
     * @throws AnswerException 
     */
    public void setaId(int aId) throws AnswerException {
        if(aId < 1) {
            throw new AnswerException("Answer id much greater than 1");
        }
        else {
            this.aId = aId;
        }
    }

    /**
     * get answer content
     * @return 
     */
    public String getaContent() {
        return aContent;
    }

    /**
     * set content for answer
     * @param aContent
     * @throws AnswerException 
     */
    public void setaContent(String aContent) throws AnswerException {
        if(aContent.equals("")) {
            throw new AnswerException("Question content can't be emty");
        }
        else{
            this.aContent = aContent;
        }
    }

    /**
     * get answer for status
     * @return 
     */
    public boolean getaStatus() {
        return aStatus;
    }

    /**
     * set answer status
     * @param aStatus 
     */
    public void setaStatus(boolean aStatus) {
        this.aStatus = aStatus;
    }

    /**
     * get answer selected
     * @return 
     */
    public boolean getaSelected() {
        return aSelected;
    }

    /**
     * set answer selected
     * @param aSelected 
     */
    public void setaSelected(boolean aSelected) {
        this.aSelected = aSelected;
    }

    /**
     * get question id
     * @return 
     */
    public int getqId() {
        return qId;
    }

    /**
     * set question id
     * @param qId
     * @throws AnswerException 
     */
    public void setqId(int qId) throws AnswerException {
        if(qId < 1) {
            throw new AnswerException("Question id much greater than 0");
        }
        else {
            this.qId = qId;
        }
    }
    
    /**
     * create new answer
     * @param aId
     * @param aContent
     * @param aStatus
     * @param qId
     * @throws AnswerException 
     */
    public Answer(int aId, String aContent, boolean aStatus, int qId) throws AnswerException {
        this.setaId(aId);
        this.setaContent(aContent);
        this.setaStatus(aStatus);
        this.setaSelected(false);
        this.setqId(qId);
    }

    /**
     * Checks if the user answer is correct or not
     * @return 
     */
    public boolean isCorrect() {
        return this.aStatus == this.aSelected;
    }
    
    @Override
    public String toString() {
        return this.aContent + '\n';
    }
}
