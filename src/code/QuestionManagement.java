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
public class QuestionManagement {

    private String Q_FILE;                  //The URL of data file that stores all questions
    private int numberOfQuestion;           //Number of questions that stored in data file
    private int qScore;                     //score of question
    private boolean checkpoint;             //is that question have checkpoint or not
    private ArrayList<Question> questions;  //All instances of questions
    private AnswerManagement am;            //Instance of AnswerManagement

    /**
     * Creates instance for question management
     *
     * @param Q_FILE
     * @param am
     * @throws quizmanagement.QuestionException
     */
    public QuestionManagement(String Q_FILE, AnswerManagement am) throws QuestionException {
        if (Q_FILE.equals("")) {
            throw new QuestionException("The URL of" + Q_FILE + "data file can't be empty!");
        } else {
            //Inits the URL of data file thats stores question bank
            this.Q_FILE = Q_FILE;

            //Creates empty question bank
            this.questions = new ArrayList<Question>();

            //So, the number of question is 0
            this.numberOfQuestion = 0;

            //Inits the answer management
            this.am = am;

            //The score will be 0
            this.qScore = 0;
        }
    }

    /**
     * Loads data of questions from data file and stored it into ArrayList
     *
     * @throws IOException
     * @throws QuestionException
     */
    public void loadQuestions() throws IOException, QuestionException {
        File qFile = new File(Q_FILE);

        //Checks is file created
        if (!qFile.exists()) {
            qFile.createNewFile(); //If not, creates new file
            System.out.println(
                    //"\n--------------------" + 
                    "The data file questions.txt is not exits. "
                    + "Creating new data file " + Q_FILE + ".txt..."
                    + "Done!");
            this.numberOfQuestion = 0; //New data file with the number of question is 0
        } else {
            //If file is existed, so loading this data file
            //Loads text file into buffer
            BufferedReader br = new BufferedReader(new FileReader(Q_FILE));
            try {
                String line, qId, qContent, qScore, checkpoint;

                //Reads number of answers
                line = br.readLine();
                this.numberOfQuestion = Integer.parseInt(line);
                qScore = br.readLine();
                this.qScore = Integer.parseInt(qScore);
                checkpoint = br.readLine();
                this.checkpoint = Boolean.parseBoolean(checkpoint);
                for (int i = 0; i < this.numberOfQuestion; i++) {
                    //Reads answer's information
                    qId = br.readLine();
                    qContent = br.readLine();

                    //Create new instance of Answer and adds to answer bank
                    this.questions.add(new Question(Integer.parseInt(qId), qContent));
                }
            } finally {
                br.close();
            }
        }
    }

    /**
     * Gets number of questions
     *
     * @return
     */
    public int getSize() {
        return this.numberOfQuestion;
    }

    /**
     * get question score
     *
     * @return
     */
    public int getScore() {
        return this.qScore;
    }

    /**
     * get question checkpoint
     *
     * @return
     */
    public boolean getCheckpoint() {
        return this.checkpoint;
    }

    /**
     * Adds new question to question bank
     *
     * @param qContent
     * @return
     * @throws QuestionException
     */
    public int addQuestion(String qContent) throws QuestionException {
        this.questions.add(new Question(++this.numberOfQuestion, qContent));
        return this.numberOfQuestion;
    }

    /**
     * Finds question by question id and return the index of this question
     *
     * @param qId
     * @return
     */
    public int findQuestion(int qId) {
        for (int i = 0; i < this.questions.size(); i++) {
            Question q = this.questions.get(i);
            if (q.getqId() == qId) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the question instance by question id
     *
     * @param qId
     * @return
     */
    public Question getQuestion(int qId) {
        int idx = this.findQuestion(qId);
        if (idx == -1) {
            return null;
        } else {
            return this.questions.get(idx);
        }
    }

    /**
     * Saves question bank (ArrayList) into data file
     *
     * @throws IOException
     */
    public void saveQuestions() throws IOException {
        //Overwrite data file
        FileWriter fw = new FileWriter(new File(Q_FILE), false);

        try {
            System.out.print(
                    //"\n--------------------" + 
                    "\nQuestions is saving into data file " + Q_FILE + ".txt...");

            //Writes number of question
            fw.append(String.valueOf(this.numberOfQuestion) + "\n");
            fw.append(String.valueOf(this.qScore) + "\n");
            fw.append(String.valueOf(this.checkpoint) + "\n");

            for (int i = 0; i < this.numberOfQuestion; i++) {
                //Inits question's information
                int qId = this.questions.get(i).getqId();
                String qContent = this.questions.get(i).getqContent();

                //Writes quesiton's information into data file
                fw.append(String.valueOf(qId) + "\n");
                fw.append(qContent + "\n");
            }
        } finally {
            //Saves data file (from RAM to HDD)
            fw.close();
        }
    }

    /**
     * Checks that the user's answer is correct or incorrect
     *
     * @param qId
     * @param answers
     * @return
     */
    public boolean isQuestionCorrect(int qId, ArrayList<Answer> answers) {
        boolean isCorrect = true;
        for (int i = 0; i < answers.size(); i++) {
            //the answer of user is correct even if the user's selected is the same with answer's status
            isCorrect = isCorrect && answers.get(i).isCorrect();
        }
        return isCorrect;
    }

    /**
     * Gets the question formatted string that includes question content and all
     * answers that comes with random mode
     *
     * @param qId
     * @return
     */
    public String showQuestion(int qId, boolean isShuffle) {
        Question q = getQuestion(qId);
        ArrayList<Answer> aList = am.getAnswers(qId, isShuffle);

        String str = "";
        str += q.toString();

        char aNo = 'a';
        for (int i = 0; i < aList.size(); i++, aNo++) {
            str += "   " + aNo + ". " + aList.get(i).toString();
        }
        return str;
    }

    /**
     * Gets the question formatted string that includes question content and all
     * answers that comes with a list of answers
     *
     * @param qId
     * @param aList
     * @return
     */
    public String showQuestion(int qId, ArrayList<Answer> aList) {
        Question q = getQuestion(qId);

        String str = "";
        str += q.toString();

        char aNo = 'a';
        for (int i = 0; i < aList.size(); i++, aNo++) {
            str += "   " + aNo + ". " + aList.get(i).toString();
        }
        return str;
    }

    /**
     * Gets the first qNumber of question bank
     *
     * @param qNumber
     * @param isShuffle
     * @return
     */
    public ArrayList<Question> getQuestionBank(int qNumber) {
        ArrayList<Question> qList = new ArrayList<Question>();

        //Inits the index of all answer
        int[] idx = new int[questions.size()];
        for (int i = 0; i < questions.size(); i++) {
            idx[i] = i;
        }

        for (int i = 0; i < qNumber; i++) {
            qList.add(questions.get(idx[i]));
        }

        return qList;
    }
}
