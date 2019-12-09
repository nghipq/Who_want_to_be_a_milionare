/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author phamq
 */
class AnswerManagement {
    private String A_FILE;              //The URL of data file that stores all answers
    private int numberOfAnswer;         //Number of answers that stored in data file
    private ArrayList<Answer> answers;  //All instances of answers
    
    /**
     * Creates instance for answer management
     * @param A_FILE 
     * @throws quizmanagement.AnswerException 
     */
    public AnswerManagement(String A_FILE) throws AnswerException {
        if (A_FILE.equals("")) {
            throw new AnswerException("The URL of answer data file can't be empty!");
        } else {
            //Inits the URL of data file thats stores answer bank
            this.A_FILE = A_FILE;

            //Creates empty answer bank
            this.answers = new ArrayList<Answer>();

            //So, the number of answer is 0
            this.numberOfAnswer = 0;
        }
    }
    
    /**
     * Loads data of answers from data file and stored it into ArrayList
     * @throws IOException
     * @throws AnswerException 
     */
    public void loadAnswers() throws IOException, AnswerException {
        File aFile = new File(A_FILE);
        
        //Checks is file created
        if(!aFile.exists()){
            aFile.createNewFile(); //If not, creates new file
            System.out.println(
                               //"\n--------------------\n" + 
                               "The data file answers.txt is not exits. " + 
                               "Creating new data file answers.txt... " + 
                               "Done!");
            this.numberOfAnswer = 0; //New data file with the number of answer is 0
        }else{
            //If file is existed, so loading this data file
            //Loads text file into buffer
            try (BufferedReader br = new BufferedReader(new FileReader(A_FILE))) {
                String line, qId, aId, aContent, aStatus;
                
                //Reads number of answers
                line = br.readLine();
                this.numberOfAnswer = Integer.parseInt(line);
                
                for (int i = 0; i < this.numberOfAnswer; i++) {
                    //Reads answer's information
                    qId      = br.readLine();
                    aId      = br.readLine();
                    aContent = br.readLine();
                    aStatus  = br.readLine();
                    
                    //Create new instance of Answer and adds to answer bank
                    this.answers.add(new Answer(Integer.parseInt(aId), aContent, Boolean.parseBoolean(aStatus), Integer.parseInt(qId)));
                }
            }
        }
    }
    
    /**
     * Adds new answer to answer bank
     * @param aContent
     * @param aStatus
     * @param qId
     * @return
     * @throws AnswerException 
     */
    public int addAnswer(int aId, String aContent, boolean aStatus, int qId) throws AnswerException {
        this.answers.add(new Answer(aId, aContent, aStatus, qId));
        this.numberOfAnswer++;
        return this.numberOfAnswer; //answer id
    }
    
    /**
     * Finds answer by answer id and return the index of this answer
     * @param aId
     * @return 
     */
    public int findAnswer(int aId) {
        for (int i = 0; i < this.answers.size(); i++) {
            Answer a = this.answers.get(i);
            if (a.getaId() == aId) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Finds the answer instance by answer id
     * @param aId
     * @return 
     */
    public Answer getAnswer(int aId) {
        int idx = this.findAnswer(aId);
        if (idx == -1) {
            return null;
        } else {
            return this.answers.get(idx);
        }
    }

    /**
     * Saves answer bank (ArrayList) into data file
     * @throws IOException 
     */
    public void saveAnswers() throws IOException {
        //Overwrite data file
        FileWriter fw = new FileWriter(new File(A_FILE), false);
        try {
            System.out.print(
                               //"\n--------------------" + 
                               "\nAnswers is saving into data file answers.txt...");

            //Writes number of answer
            fw.append(String.valueOf(this.numberOfAnswer) + "\n"); 

            for(int i=0; i<this.numberOfAnswer; i++){    
                //Inits answer's information
                int qId           = this.answers.get(i).getqId();
                int aId           = this.answers.get(i).getaId();
                String aContent   = this.answers.get(i).getaContent();
                boolean aStatus   = this.answers.get(i).getaStatus();

                //Writes answer's information into data file
                fw.append(String.valueOf(qId) + "\n");
                fw.append(String.valueOf(aId) + "\n"); 
                fw.append(aContent + "\n"); 
                fw.append(String.valueOf(aStatus) + "\n");  
            }            
        } finally {
            //Saves data file (from RAM into HDD)
            fw.close();
        }
    }    
    
    /**
     * Gets all answer that belongs to question that identifies by question id
     * @param qId
     * @return 
     */
    public ArrayList<Answer> getAnswers(int qId, boolean isShuffle) {
        ArrayList<Answer> aList = new ArrayList<Answer>();
        
        for (int i = 0; i < this.answers.size(); i++) {
            Answer a = this.answers.get(i);
            if (a.getqId() == qId) {
                aList.add(a);
            }
        }
        
        //Inits the index of all answer
        int[] idx = new int[aList.size()];
        for (int i = 0; i < aList.size(); i++) {
            idx[i] = i; 
        }
        
        if (isShuffle) { //if the random mode is turned on
            int newIdx, tmp;
            Random ran = new Random();
            
            //Randomizes indexes of answer bank
            for (int i = 0; i < aList.size(); i++) {
                newIdx = ran.nextInt(aList.size());
                tmp = idx[i];
                idx[i] = idx[newIdx];
                idx[newIdx] = tmp;
            }
        }
        
        ArrayList<Answer> result = new ArrayList<Answer>();
        for (int i = 0; i < aList.size(); i++) {
            result.add(aList.get(idx[i]));
        }
       
        return result;
    }
}
