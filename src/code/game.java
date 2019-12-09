/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author phamq
 */
public class game {

    private static AnswerManagement am;
    private static QuestionManagement qm;

    /**
     * set time for code run
     *
     * @param runnable
     * @param delay
     */
    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    /**
     * Make answer list from 4 to 2 with 1 correct answer and 1 incorrect answer
     *
     * @param aList
     * @return
     */
    public static String fiftyPerFifty(ArrayList<Answer> aList) {
        String str = "";
        int idx1 = 0, idx2;

        Random ran = new Random();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < aList.size(); j++) {
                if (aList.get(j).getaStatus()) {
                    idx1 = j;
                    break;
                };
            }
            do {
                idx2 = ran.nextInt(aList.size());
            } while (idx2 == idx1);

            aList.remove(idx2);
        }

        char aNo = 'a';
        for (int i = 0; i < 2; i++, aNo++) {
            str += "   " + aNo + ". " + aList.get(i).toString();
        }

        return str;
    }

    /**
     * show the correct question
     *
     * @param aList
     * @return
     */
    public static String askAudience(ArrayList<Answer> aList) {
        Random ran = new Random();
        String str = "";
        int idx;
        int perCent = 30;
        char aNo = 'a';

        for (int i = 0; i < aList.size(); i++) {
            int tmp = 0;

            if (i == aList.size() - 1) {
                tmp = perCent;
            } else {
                tmp = ran.nextInt(perCent + 1);
            }

            perCent -= tmp;
            if (aList.get(i).getaStatus()) {
                tmp += 70;
                str += "  " + tmp + "% choose " + aNo;
            } else {
                str += "  " + tmp + "% choose " + aNo;
            }
            str += "\n";
            aNo++;
        }

        return str;
    }

    /**
     * return the correct answer
     *
     * @param aList
     * @return
     */
    public static void callSomeOne(ArrayList<Answer> aList) {
        Scanner sc = new Scanner(System.in);
        Random ran = new Random();

        String[] person = {"Quach Luyl-Da", "Minh Bui", "Vo Hong Khanh", "Mr.Bay"};
        System.out.println("Who do you want to call?");

        for (int i = 0; i < person.length; i++) {
            System.out.println((i + 1) + " " + person[i]);
        }

        int choose = 0;
        do {
            System.out.print("  >>> Please choose the person you want to call "
                    + "(from 1 to " + person.length + "):");
            choose = sc.nextInt();
        } while (choose <= 0 || choose > person.length);
        char aNo = 'a';
        if (choose % 2 != 0) {
            for (int i = 0; i < aList.size(); i++) {
                if (aList.get(i).getaStatus()) {
                    break;
                } else {
                    aNo++;
                }
            }
        } else {
            aNo += ran.nextInt(aList.size());
        }
        System.out.println(person[choose - 1] + " said: " + aNo + " is the correct answer");
    }

    /**
     * Create new question for the game
     *
     * @throws AnswerException
     * @throws IOException
     * @throws QuestionException
     */
    public static void func1() throws AnswerException, IOException, QuestionException {
        Scanner sc = new Scanner(System.in);
        System.out.println("------GAME MANAGEMET [ADD NEW QUESTION]-----");
        int level = 0;

        do {
            System.out.print("\nWhat level you want to add new question?(1-15): ");
            String chooseln = sc.nextLine();
            if(chooseln.equals("1")||chooseln.equals("2")||chooseln.equals("3")|| 
                    chooseln.equals("4")||chooseln.equals("5")||chooseln.equals("6")||chooseln.equals("7")||
                    chooseln.equals("8")||chooseln.equals("9")||chooseln.equals("10")||chooseln.equals("11")||
                    chooseln.equals("12")||chooseln.equals("13")||chooseln.equals("14")||chooseln.equals("15")){
                level = Integer.parseInt(chooseln);
            }
            if (level < 1 || level > 15) {
                System.out.println("ERROR: You much input between 1 and 15");
            }
        } while (level < 1 || level > 15);
        //make file name
        String FILE = level + ".txt";
        //load answer file
        am = new AnswerManagement("src/data/Answers/" + FILE);
        am.loadAnswers();
        ArrayList<Answer> aList;
        //load question file
        qm = new QuestionManagement(("src/data/Questions/" + FILE), am);
        qm.loadQuestions();

        //input new question content
        String qContent = "";
        do {
            System.out.print("Please enter content question: ");
            qContent = sc.nextLine();
            if (qContent.equals("")) {
                System.out.println("Error: Question can't be emty");
            }
        } while (qContent.equals(""));

        //get qId
        int qId = qm.addQuestion(qContent);
        System.out.println("Your question is create!");
        System.out.println("++++[ADD ANSWERS FOR QUESTION]");
        String ip;

        //input 4 answers for question
        int countF = 0;
        int countT = 0;
        for (int aNo = 1; aNo <= 4; aNo++) {
            int check = 0;
            System.out.println("Answer " + aNo + "...");
            String content = "";
            boolean status = false;
            //input answer's content
            do {
                System.out.print("Please enter content answer" + aNo + ":");
                content = sc.nextLine();
                if (content.equals("")) {
                    System.out.println("Error: answer content can't be emty");
                }
            } while (content.equals(""));
            //input that answer correct or not
            if (countF < 3) {
                do {
                    System.out.print("Is answer True or False(True/False): ");
                    ip = sc.nextLine();
                    
                    if (ip.equalsIgnoreCase("True")) {
                        if (countT == 1) {
                            System.out.println("ERROR: You can't create 2 true answer");
                            check = 1;
                        } else {
                            status = true;
                            countT += 1;
                        }
                    } else if (ip.equalsIgnoreCase("False")) {
                        status = false;
                        countF += 1;
                    } else {
                        System.out.println("Error: You much type True or False");
                    }
                } while (!(ip.equalsIgnoreCase("True") || ip.equalsIgnoreCase("False")));
            } else {
                status = true;
            }
            //add new answer    
            if (check == 1) {
                aNo--;
            } else {
                am.addAnswer(aNo,content, status, qId);
            }
        }
        //save answers
        try {
            am.saveAnswers();
        } catch (Exception e) {
            System.out.println("Can't save answer");
        }
        //save question    
        try {
            qm.saveQuestions();
        } catch (Exception e) {
            System.out.println("Can't save question");
        }

        System.out.println("Create answers successful!");
    }

    /**
     * Find question by id and edit question content
     *
     * @throws QuestionException
     * @throws IOException
     */
    public static void func2() throws QuestionException, IOException, AnswerException {
        Scanner sc = new Scanner(System.in);
        System.out.println("------GAME MANAGEMET [EDIT QUESTION CONTENT]-----");
        int level = 1;
        //input question level
        do {
            System.out.print("\nWhat level you want to edit question?(1-15): ");
            level = sc.nextInt(); sc.nextLine();
            if (level < 1 || level > 15) {
                System.out.println("ERROR: You much input between 1 and 15");
            }
        } while (level < 1 || level > 15);
        //make file name
        String FILE = level + ".txt";
        //load answer file
        am = new AnswerManagement("src/data/Answers/" + FILE);
        am.loadAnswers();
        //load question file
        qm = new QuestionManagement(("src/data/Questions/" + FILE), am);
        qm.loadQuestions();
        //input question id
        int qId = 0;
        do {
            String inp = "";
            System.out.print(" >>> Please enter question's ID: ");
            qId = sc.nextInt();
            sc.nextLine();
            if (qId < 1 || qId > qm.getSize()) {
                System.out.println("ERROR: Please enter between 1 and " + qm.getSize());
            }
            do {
                System.out.println(qm.getQuestion(qId).getqContent());
                System.out.print("Is that your finded question?(y/n): ");
                inp = sc.nextLine();
                if (!(inp.equals("y") || inp.equals("n"))) {
                    System.out.println("Please input y or n!");
                }
            } while (!(inp.equals("y") || inp.equals("n")));
            if (inp.equals("n")) {
                qId = 0;
            }
        } while (qId < 1 || qId > qm.getSize());

        //input new content
        String newContent = "";
        do {
            System.out.print(" >>> Please enter new content for question " + qId + ": ");
            newContent = sc.nextLine();
            if (newContent.equals("")) {
                System.out.println("Error: question content can't be emty");
            }
        } while (newContent.equals(""));
        //set new content
        qm.getQuestion(qId).setqContent(newContent);
        //save question
        try {
            qm.saveQuestions();
        } catch (Exception e) {
            System.out.println("Can't save question");
        }
    }

    public static void func3() throws AnswerException, IOException, QuestionException {
        Scanner sc = new Scanner(System.in);
        System.out.println("------GAME MANAGEMET [EDIT ANSWER CONTENT]-----");
        int level;
        //input question level
        do {
            System.out.print("\nWhat level you want to edit question?(1-15): ");
            level = sc.nextInt();
            sc.nextLine();
            if (level < 1 || level > 15) {
                System.out.println("ERROR: You much input between 1 and 15");
            }
        } while (level < 1 || level > 15);
        //make file name
        String FILE = level + ".txt";
        //load answer file
        am = new AnswerManagement("src/data/Answers/" + FILE);
        am.loadAnswers();
        //load question file
        qm = new QuestionManagement(("src/data/Questions/" + FILE), am);
        qm.loadQuestions();
        //input question id
        int qId = 0;
        do {
            String inp = "";
            System.out.print(" >>> Please enter question's ID: ");
            qId = sc.nextInt();
            sc.nextLine();
            if (qId < 1 || qId > qm.getSize()) {
                System.out.println("ERROR: Please enter between 1 and " + qm.getSize());
            }
            do {
                System.out.println(qm.getQuestion(qId).getqContent());
                System.out.print("Is that your finded question?(y/n): ");
                inp = sc.nextLine();
                if (!(inp.equals("y") || inp.equals("n"))) {
                    System.out.println("Please input y or n!");
                }
            } while (!(inp.equals("y") || inp.equals("n")));
            if (inp.equals("n")) {
                qId = 0;
            }
        } while (qId < 1 || qId > qm.getSize());
        //input answer id
        int aId = 1;
        do {
            String inp = "";
            System.out.print(" >>> Please enter answer's ID: ");
            aId = sc.nextInt();
            sc.nextLine();
            if (aId < 1 || aId > 4) {
                System.out.println("ERROR: Please enter between 1 and 4");
            }
            do {
                System.out.println(am.getAnswers(qId, false).get(aId - 1).getaContent());
                System.out.print("Is that your finded answer?(y/n): ");
                inp = sc.nextLine();
                if (!(inp.equals("y") || inp.equals("n"))) {
                    System.out.println("Please input y or n!");
                }
            } while (!(inp.equals("y") || inp.equals("n")));
            if (inp.equals("n")) {
                aId = 0;
            }
        } while (aId <= 0 || aId > 4);
        //input new content
        String newContent = "";
        do {
            System.out.print(" >>> Please enter new content for answer " + aId + ": ");
            newContent = sc.nextLine();
            if (newContent.equals("")) {
                System.out.println("Error: answer content can't be emty");
            }
        } while (newContent.equals(""));
        am.getAnswers(qId, false).get(aId - 1).setaContent(newContent);
        //save answers
        try {
            am.saveAnswers();
        } catch (Exception e) {
            System.out.println("Can't save answer");
        }
    }

    /**
     * play the game
     *
     * @throws AnswerException
     * @throws IOException
     * @throws QuestionException
     */
    public static void func4() throws AnswerException, IOException, QuestionException {
        Random ran = new Random();
        Scanner sc = new Scanner(System.in);
        //
        ArrayList<String> spList = new ArrayList<String>();
        spList.add("50-50");
        spList.add("Call someone");
        spList.add("Ask the Audien");
        //introduce the game
        System.out.println("###############################");
        System.out.println("#     WELCOME TO GAME SHOW    #");
        System.out.println("# WHO WANT TO BE A MILLIONARE #");
        System.out.println("###############################");
        System.out.println("-------------------------------");
        setTimeout(() -> System.out.println("In this game, you have to passed "
                + "15 question to be a winner!\n"), 0);
        setTimeout(() -> System.out.println("Each question corresponds to 1 "
                + "amount\n"), 0);
        setTimeout(() -> System.out.println("There are 2 checkpoint to save "
                + "your money: question 5 and question 10\n"), 0);
        setTimeout(() -> System.out.println("You will have 3 support: 50-50, call"
                + "someone and ask the audience\nIf you want support, input 'sp'"
                + "\n"), 0);
        setTimeout(() -> System.out.println("If you want to stop the game and "
                + "get the money at the last question, input 'stop'\n"), 0);
        //asign user input
        setTimeout(() -> System.out.print("Are you ready?[y/n]: "), 0);
        String ip = sc.nextLine();
        while (!ip.equals("y")) {
            System.out.print("Please input y when you ready: ");
            ip = sc.nextLine();
        }
        System.out.println("\nOK! Now let go!");
        //asign some variable
        int finalScore = 0, Score = 0;
        //loop over 15 level of question
        for (int i = 1; i <= 15; i++) {
            //make file name
            String FILE = i + ".txt";
            //load answer file
            am = new AnswerManagement("src/data/Answers/" + FILE);
            am.loadAnswers();
            ArrayList<Answer> aList;
            //load question file
            qm = new QuestionManagement(("src/data/Questions/" + FILE), am);
            qm.loadQuestions();

            //load question information
            Question q;
            //load number of question
            int numberOfQuestions = qm.getSize();
            //load score of question
            int qScore = qm.getScore();
            //is that question have checkpoint or not
            boolean checkpoint = qm.getCheckpoint();

            //get list of question
            ArrayList<Question> qList = qm.getQuestionBank(numberOfQuestions);
            //make question by random
            int ques = ran.nextInt(numberOfQuestions);
            q = qList.get(ques);
            int qId = q.getqId();
            aList = am.getAnswers(qId, true);

            //print user score
            if (i > 1) {
                System.out.println("Now, you have " + Score + " big coins");
                System.out.println("If you have a true answer at next question,"
                        + "your money will be " + qScore + " big coins");
            }

            //print question
            System.out.println("\n##########");
            System.out.println("Question " + i + ": " + qm.showQuestion(qId, aList));

            //input answer
            char ans;
            do {
                System.out.print("  >>>> Please select answer: ");
                ip = sc.nextLine();
                ip = ip.toLowerCase();
                if (ip.equals("stop")) {
                    break;
                } else if (ip.equals("sp")) {
                    ans = 'e';
                    if (spList.size() == 0) {
                        System.out.println("You have exhausted all rights to help");
                    } else {
                        int sp;
                        System.out.println("What support do you want to use?(from 1 to " + spList.size() + ")");
                        for (int j = 0; j < spList.size(); j++) {
                            System.out.println((j + 1) + ". " + spList.get(j));
                        }

                        do {
                            System.out.print(" >>> Your choice: ");
                            sp = sc.nextInt();
                            sc.nextLine();
                            if (sp < 1 || sp > spList.size()) {
                                System.out.println("You much input the valid number!");
                            }
                        } while (sp < 1 || sp > spList.size());

                        switch (spList.get(sp - 1)) {
                            case "50-50":
                                System.out.println(fiftyPerFifty(aList));
                                break;
                            case "Call someone":
                                callSomeOne(aList);
                                break;
                            case "Ask the Audien":
                                System.out.println(askAudience(aList));
                                break;
                        }

                        spList.remove(sp - 1);
                    }
                } else {
                    ans = ip.charAt(0);
                    if ((ans < 'a' || ans > 'a' + aList.size() - 1)) {
                        System.out.printf("Error: your answer much be from a to %c\n", ('a' + aList.size() - 1));
                    } else {
                        aList.get(ans - 'a').setaSelected(true);
                    }
                }
            } while (ans < 'a' || ans > 'a' + aList.size() - 1);

            if (ip.equals("stop")) {
                finalScore = Score;
                System.out.println("You were stop this game!");
                break;
            }

            boolean isUserCorrect = qm.isQuestionCorrect(qId, aList);
            if (isUserCorrect) {
                System.out.println("CONRATULATION! This is a good answer\n");

                if (checkpoint) {
                    finalScore = qScore;
                }
                Score = qScore;
            } else {

                System.out.println("SORRY! This is a wrong answer!\n");
                System.out.println("YOU LOSE!\n");
                break;
            }
        }
        System.out.println("You got " + finalScore + " big coins");
        System.out.println("THANKS YOU FOR PLAYING THIS GAME! SEE YOU AGAIN!");
    }

    public static void main(String args[]) throws AnswerException, IOException, QuestionException {
        Scanner sc = new Scanner(System.in);
        String chooseln = "";
        int choose = 0;
        //print menu
        System.out.println("\n------WHO WANT TO BE A MILLIONARE--------------\n");
        System.out.println("1. Create new question");
        System.out.println("2. Find question by id and change question content");
        System.out.println("3. Find answer by id and change answer content");
        System.out.println("4. PLAY");
        System.out.println("5. Quit");
        do {
            //input between 1 and 5 to do the function
            System.out.print("\n>>> Your choice (from 1 to 5): ");
            chooseln = sc.nextLine();
            if(chooseln.equals("1")||chooseln.equals("2")||chooseln.equals("3")|| chooseln.equals("4")||chooseln.equals("5")) {
                choose = Integer.parseInt(chooseln);
            }
            

            //if choose < 1 or > 5 print error
            if (choose < 1 || choose > 5) {
                System.out.println("ERROR: Invalid function, please choose between 1 and 5");
            }

            switch (choose) {
                case 1:
                    func1();
                    break;
                case 2:
                    func2();
                    break;
                case 3:
                    func3();
                    break;
                case 4:
                    func4();
                    break;
                case 5:
                    break;
            }
        } while (choose != 5);

        System.out.println("THANKS FOR USING OUR PROGRAM!");
        System.out.println("SEE YOU NEXT TIME!");
    }
}
