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
public class Question {
    private int qId; //question id
    private String qContent;//question content

    /**
     * get id for question
     * @return 
     */
    public int getqId() {
        return qId;
    }

    /**
     * set the id for question
     * @param qId
     * @throws QuestionException 
     */
    public void setqId(int qId) throws QuestionException {
        if(qId < 1) {
            throw new QuestionException("Question id much greater than 0");
        }
        else {
            this.qId = qId;
        }
    }

    /**
     * get content for question
     * @return 
     */
    public String getqContent() {
        return qContent;
    }

    /**
     * set content for question
     * @param qContent
     * @throws QuestionException 
     */
    public void setqContent(String qContent) throws QuestionException {
        if(qContent.equals("")) {
            throw new QuestionException("Question content can't be emty");
        }
        else{
            this.qContent = qContent;
        }
    }
    
    /**
     * create new question
     * @param qId
     * @param qContent
     * @throws QuestionException 
     */
    public Question(int qId, String qContent) throws QuestionException {
        this.setqId(qId);
        this.setqContent(qContent);
    }

    @Override
    public String toString() {
        return this.qContent + '\n';
    }
}
