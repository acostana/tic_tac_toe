//Ana Acosta
//11.25.19
//TicTacToe.java

import java.util.Scanner;
import java.util.Random;
import java.io.*;

//This program lets the user play tic tac toe against the computer. It also prints the board to a document each move.

public class TicTacToe{
   
   /*
   This method prints out messages and prompts the user to enter a symbol or letter, then creates an instance of an object of type Symbol passing the user's
   input to the constructor and then uses methods setSymbol and getSymbol from Symbol class to set and return the user's mark. Method instructions 
   is then called and assigns its returning boolean value to continueOrNot. The method play is then called and passed to it continueOrNot and the user's mark. 
   */
   public static void main(String[] args)throws IOException{ //opens method
      System.out.println("********Welcome to my tic tac toe game!********");
      System.out.println();
      System.out.println("Enter a symbol or letter that you want to use to mark your boxes (except for capital letter 'O'. The computer will use this!!!): ");
      Scanner input = new Scanner(System.in);     
      String symbol = input.nextLine();
      Symbol userSymbol = new Symbol(symbol);
      userSymbol.setSymbol(symbol);
      String mark = userSymbol.getSymbol();
      boolean continueOrNot = instructions();
      play(continueOrNot,mark);
   } //closes method
   
   
   /*
   This method prints out instructions and and original board and then prompts the user to enter x to continue. takes in nothing but it returns a boolean.
   */
   public static boolean instructions(){ //opens method
      boolean play = false; //play is set to false
      Scanner keyboard = new Scanner(System.in);//creates an instance of Scanner type object
      System.out.println("This is what the board looks like: ");
      System.out.println();
      printOriginalBoard();//calls printOriginalBoard methid
      System.out.println("The rules of this game are simple. You pick a number where you want to place your mark.");
      System.out.println("Then the computer will pick a number and place its mark");
      System.out.println("Once a player completes a row or column or diagonal line. They win!");
      System.out.println("Good Luck! :)");
      System.out.println();
      System.out.println("Press x to continue");
      String input = keyboard.nextLine();
      /*
      Sets play to true if user's input is upper case or lower case x. Otherwise the default case prints message and ends program
      */
      switch(input){ //opens switch
         case "x":
         case "X":
            play = true;
            break;
        default : 
            System.out.println("Goodbye");
      }//closes switch
      System.out.println();
      return play;
   } //closes method
   
   
   /*
   Method returns nothing but takes in a boolean and a string.
   */
   public static void play(boolean yesOrNo, String symbol)throws IOException{//opens method
      /*
      If boolean passed to method is true the loop will run. Sets winner to false as a flag. Creates a reference to an array of type String
      named board which contains the numbers that will be printed on the tic tac toe board. Creates reference to two other arrays of length 8 and 
      type String that will store the user's and the computer's inputs organized by rows, columns, and diagonals. An 2d array of type int is created
      containing the rows, columns, and diagonals of the board. This loop contains an inner  while loop.
      */
      while(yesOrNo == true){//opens outer while loop
         boolean winner = false;
         String[] board = {"1","2","3","4","5","6","7","8","9"};
         String [] userNums = new String[8]; //this will contain user inputs organized by rows, cols, diags. (ex. "123", "45", "678", "17"..)
         String [] computerNums = new String[8];
         for (int i = 0; i<userNums.length; i++){ //populates the array userNums, starts off with empty strings but will be updated later
           userNums[i] = ""; 
         }
         for(int i = 0; i<computerNums.length; i++){ //populates computerNums, starts off with empty strings but will be updated later
            computerNums[i] = "";
         }
         int[][] available = {{1,2,3}, {4,5,6},{7,8,9},{1,4,7},{2,5,8},{3,6,9},{1,5,9},{3,5,7}};
         
         /*
         Will run if winner is false until it is set to true. Calls function printAvailable to print available numbers to choose from. 
         Prompts user for input which is then converted to a String to be printed to the board***see note***. Calls updateBoard and printFile passing to 
         it the array board, the user's num converted to string and the user's symbol or mark to place the user's mark on the board and to "write" the board 
         to a text file. Calls updateStringArr passing to it the array userNums and the user's num as an integer to update the array that holds the
         user's inputs organized by rows, columns, and diagonals. Calls updateAvailable to update the list of available numbers to choose from, passing
         to it the user's integer, and the available array. Then calls checkString passing the user's inputs, winner, and the available array to check
         if the user has completed a row, column, or diagonal, teh boolean this function returns is set to winner. I had to created a conditional statement 
         to break out of the loop if winner was true because for some reason it wouldn't break from it when it was set to true. Similar process for 
         when it's the computer's turn with a few modifications.
         */
         while(winner == false){//opens inner loop
            printAvailable(available);
            System.out.println();
            int userNum = getUserNum();
            String userStringNum = Integer.toString(userNum);//Note: I had already written the other methods that used user's input as an integer so I didn't want to change them and just created a new String object of it so the board could be updated
            System.out.println();            
            updateBoard(board,userStringNum, symbol);
            printFile(board, userStringNum, symbol);
            updateStringArr(userNums, userNum);
            updateAvailable(userNum, available);
            winner = checkString(userNums, winner, available);//Note: checked and this was true but loop didn't exit for some reason
            if(winner== true){
              break;
            }
            System.out.println("\n\n");
            /*
            Same process as above with user input but instead of asking user for input the computer is asked for an input using the computerChoose method
            passing to it the list of available numbers to choose from, the user inputs, and the computer inputs.The number the computer
            chooses is then used for the other methods. 
            */
            int computerNum = computerChoose(available, userNums, computerNums);
            String computerStringNum = Integer.toString(computerNum);
            updateBoard(board,computerStringNum, "O"); //when updating and "writing" the  board the computer's mark is "O"
            printFile(board, computerStringNum, symbol);
            System.out.println("\n\n");
            updateStringArr(computerNums, computerNum);
            updateAvailable(computerNum, available);
            winner = checkString(computerNums,winner, available);
            if(winner==true){
             break;
            }
           
         }//closes inner loop
         System.out.println("Would you like to play again? Y or N");// after inner loop is finished user is asked if they want to play again
         Scanner keyboard = new Scanner(System.in);
         String input = keyboard.nextLine();
         /*
         If the user's input is either an upper case or lower case Y then the boolean that the method takes in is set to true so that the outer
         loop can iterate again and start the game all over again. If the input is upper or lower case N or something else then it is set to false and
         the program exits the outer while loop. 
         */
         switch(input){ //opens switch
            case "Y":
            case "y":
               yesOrNo = true;
               break;
            case "N":
            case "n":
               yesOrNo = false;
            default : 
               System.out.println("Goodbye");
               yesOrNo = false; 
         }//closes switch
     }//closes outer while loop

   }//closes method
   
   
   /*
   Takes in an array, and two strings, the number to be replaced on the board as a String and the mark that will replace it. Retunrs nothing.
   */
   public static void printFile(String[] arr, String num, String symbol)throws IOException{//opens method
        FileWriter writer = new FileWriter("boards.txt",true);  //creates instance of FileWriter passing to the contructor a text file that will be
                                                                  //appended to if it already exists
         /*
         Iterates through each number(String) on the board and if a number(String) matches the number(String) passed to the method then that element is 
         replaced with the mark that was passed to the method. By the end of the nested for loop, all numbers, lines, and symbols will be written to 
         the text file.It prints a blank line after each board
         */
         for(int i=1; i<=7; i+=3){//opens outer loop
           for (int j =i; j<(i + 3); j++){//opens inner loop
               if(arr[j-1].equals(num)){
                  arr[j-1] = symbol;
               }
               writer.write(arr[j-1] + " | ");
            }//closes inner loop
         writer.write("\r\n");
         writer.write( "-----------" + "\r\n");
         } //closes outer loop
         writer.write("\r\n");
       writer.close();//closes writer
   }//closes method
   

   /*
   This method takes in reference to a 2d integer array, and two string arrays. It returns an integer which is the number that the computer chooses, or the 
   computer's "input". It chooses this number based on the contents of the array containing the available numbers or boxes to choose from, how close
   the user is to winning, and how close the computer is to winning.
   */
   public static int computerChoose(int[][] available, String[] userNums, String[] computerNums){//opens method
      Random rand = new Random(); //creates instance of type Random
      int computerNum = 0; //this will be the returned value
      int randRowIndex = 0; //when choosing from the available array this will be the index of an array 
      int randColIndex = 0; //when choosing from the available array this is the index of the element inside and array
      
      /*
      The options represent the possible situations, in the game, based on these the computer will choose its number. For example the first option
      will be set to true if the computer is close to winning, the second one will be true if the user is close to winning, and the third will be set to
      true if the computer has only one number in a given row, column, diagonal.
      */
      boolean[] options = {false,false,false}; //each of the options is set to false
      int optionsIndex0=0; //these indexes refer to the rows, columns, or diagonals where each situation is true
      int optionsIndex1=0;
      int optionsIndex2=0;
      
      /*
      This loop iterates through the computer inputs, computerNums, and if the current string(row, column, or diagonal) has a length of two and the length
      of available is greater than zero(there are still remaining boxes in that row) then the first option is set to true and the index is set to the index
      of the element that meets the conditions. The loop is exited as soon as the condition is met.
      */
      for(int i = 0; i<computerNums.length; i++){//opens loop
         if((computerNums[i].length()==2) && (available[i].length > 0)){//opens if statement
            options[0]= true;
            optionsIndex0 = i;
            break;
         }//closes if statement
      }//closes loop
      
      /*
      This loop iterates through the user inputs, userNums, and if the length of a string(row, column, or diagonal) is 2 and available is greater 
      than zero(there are still remaining boxes in that row, col, diag) then the second option is set to true and the index is set to the index
      of the element that meets the conditions. The loop is exited as soon as the condition is met.
      */
      for(int i =0; i<userNums.length;i++){//opens loop
         if((userNums[i].length()==2)&&(available[i].length>0)){//opens if statement
            options[1] = true;
            optionsIndex1= i;
            break;
         }//closes if statement
      }//closes loop
      
      /*
      This loop iterates through computerNums and if the length is 1 and the length of available boxes is greater than 1, then the third option is set 
      to true and the index is set to the index of the element where the condition is met. As soon as a row, column, or diagonal meets
      the condition the loop exits.
      */
      for(int i = 0;i<computerNums.length; i++){//opens loop
         if((computerNums[i].length()==1) && (available[i].length>1)){//opens if statement
            options[2] = true;
            optionsIndex2=i;
            break;
         }//closes if statement
      }//closes loop
    
      /*
      The next if else statements select a number based on priority and whether the conditions above were met(at least one of the options
      was set to true). If the first option (options[0] is true that means that the computer is close to winning so the computer chooses the last available box
      to place its mark. If that's not true then it will move on and see if the second option is true, if so that means that the user is close to winning
      so the computer chooses the last box available. If this statement is not true then it moves on and if the third condition is true that means that the 
      computer has placed one mark in a row or column or diagonal and the other two boxes are still available so it uses random to choose one of the boxes.
      If none of the above statements are true then that means that it really doesn't matter where the computer places its mark so it randomly chooses a box.
      */
      if(options[0]==true){//opens if statement
         computerNum = available[optionsIndex0][0];
      }else if(options[1]==true){
         computerNum = available[optionsIndex1][0];
      }else if(options[2]==true){
         randRowIndex= rand.nextInt(available[optionsIndex2].length);
         computerNum = available[optionsIndex2][randRowIndex];
      }else{
         while(true){//this loop iterates until the if statement is true
            int sampleIndex = rand.nextInt(available.length);//this chooses a row, column, or diagonal randomly
            if(!(available[randRowIndex].length<1)) { //this ensures that the row, column, or diagonal that was chosen randomly is not empty
               randRowIndex = sampleIndex;
               break;
            }//closes if statement  
         }//closes loop
         randColIndex = rand.nextInt(available[randRowIndex].length);//this chooses a random index from the row, column, or diagonal chosen
         computerNum = available[randRowIndex][randColIndex]; //this assigns the element selected using random to computerNum
         
      }//closes else statement
      
      return computerNum;//returns number chosen by the computer
   }//closes method

   
   /*
   This method prints the available numbers with no duplicates, takes in a reference to an array and returns nothing
   */
    public static void printAvailable(int[][] arr){//opens method
      System.out.println("Available numbers: ");
      /*
      This for loop will iterate through each element in every array and print it 
      */
      for(int i = 0; i<3; i++){//opens outer loop
         for(int j = 0; j<arr[i].length; j++){//opens inner loop
            System.out.print(arr[i][j] + ", ");
         }//closes inner loop
      }//closes outer loop
      System.out.println();
   }//closes method
   
   
   /*
   This method updates the available array by calling another method that removes elements from an array.This method takes
   in an integer and a reference to a 2d array.
   */
   public static void updateAvailable(int num, int[][] arr){//opens method
      /*
      In order to remove an element we need to know the index so the index of each row, column, and diagonal are set to -1 in the next few lines, this number
      will be updated as the if else statements are checked.
      
      row 1 = 123, row 2 = 234.... col 1 = 147, col2 = 258...diag 1 = 159, diag 2 = 357
      */
      int row1Index =-1;
      int row2Index = -1;
      int row3Index = -1;
      int col1Index = -1;
      int col2Index = -1;
      int col3Index = -1;
      int diag1Index = -1;
      int diag2Index = -1;
      /*
      The next if else statements check to see what the number passed to the method is. Depending on what the value is the  array is updtaed. For 
      example if the number passed is 1 then it will be removed from the first row, the first diagonal, and the first column since that's where 1 shows up. 
      When a statement is evaluated true it calls findIndex and passes to it the number and a corresponding array of the available array. 
      Then updateArray is called to remove that element.
      */
      if (num==1){
          row1Index = findIndex(num, arr[0]); //the integer and array where element shows up is passed to findIndex, this returns the index of the element within the array
          col1Index = findIndex(num, arr[3]);
          diag1Index = findIndex(num, arr[6]);
          arr[0] = updateArray(row1Index, arr[0]);//each array is updated passing to it the index from findIndex and the array it will be removed from
          arr[3] = updateArray(col1Index,arr[3]);
          arr[6] = updateArray(diag1Index,arr[6]);
      }else if (num==2){
         row1Index = findIndex(num,arr[0]);
         col2Index = findIndex(num,arr[4]);
         arr[0] = updateArray(row1Index,arr[0]);
         arr[4] = updateArray(col2Index, arr[4]);
      }else if (num==3){
         row1Index = findIndex(num,arr[0]);
         col3Index = findIndex(num,arr[5]);
         diag2Index = findIndex(num,arr[7]);
         arr[0] = updateArray(row1Index, arr[0]);
         arr[5] = updateArray(col3Index, arr[5]);
         arr[7] = updateArray(diag2Index, arr[7]);
      }else if (num==4){
         row2Index = findIndex(num, arr[1]);
         col1Index = findIndex(num, arr[3]);
         arr[1] = updateArray(row2Index, arr[1]);
         arr[3] = updateArray(col1Index, arr[3]);
      }else if (num==5){
         row2Index = findIndex(num,arr[1]);
         col2Index = findIndex(num, arr[4]);
         diag1Index = findIndex(num, arr[6]);
         diag2Index = findIndex(num, arr[7]);
         arr[1] = updateArray(row2Index, arr[1]);
         arr[4] = updateArray(col2Index, arr[4]);
         arr[6] = updateArray(diag1Index, arr[6]);
         arr[7] = updateArray(diag2Index, arr[7]);
      }else if (num ==6){
         row2Index = findIndex(num,arr[1]);
         col3Index = findIndex(num, arr[5]);
         arr[1] = updateArray(row2Index, arr[1]);
         arr[5] = updateArray(col3Index, arr[5]);
      }else if (num==7){
         row3Index = findIndex(num,arr[2]);
         col1Index = findIndex(num, arr[3]);
         diag2Index = findIndex(num,arr[7]);
         arr[2] = updateArray(row3Index, arr[2]);
         arr[3] = updateArray(col1Index, arr[3]);
         arr[7]= updateArray(diag2Index, arr[7]);
      }else if (num==8){
         row3Index = findIndex(num, arr[2]);
         col2Index = findIndex(num, arr[4]);
         arr[2] = updateArray(row3Index, arr[2]);
         arr[4] = updateArray(col2Index, arr[4]);
      }else if (num==9){
         row3Index = findIndex(num, arr[2]);
         col3Index = findIndex(num, arr[5]);
         diag1Index = findIndex(num, arr[6]);
         arr[2] = updateArray(row3Index, arr[2]);
         arr[5] = updateArray(col3Index, arr[5]);
         arr[6] = updateArray(diag1Index, arr[6]);
      }
   }//closes method
   
   
   /*
   This method finds the index of an element within an array. Takes in an integer, an array, and returns an integer
   */
   public static int findIndex(int num, int[] arr){
      int i = 0; //conditional variable for while loop is initialized
      int index = -1; //index is set to -1
      /*
      Loop iterates three times or until broken. It iterates through the array passed to it and checks if the number passed to method
      matches the element in the array of the current iteration, is so then index is set to the index of the element of the current iteration and exits loop,
      if they don't match then it just moves on to the next iteration.
      */
      while (i<3){ //opens while loop
         if (arr[i]==num){ //opens if else statements
           index = i;
           break; 
         }else{
            i++; 
         }  //closes if else statements
      } //closes while loop
      return index; //returns index
   }//closes method
   
   
   /*
   This method takes in an integer and an array of integers, it returns a reference to an array of integers. This method uses arraycopy to remove elements 
   from the array passed to it. The updated array is returned
   */
   public static int[] updateArray(int index, int arr[]){//opens method
      int update[] = new int[arr.length -1]; //a new array is created with length 1 less than that of the array passed to method
      /*
      The elements of the array passed to method are copied to the new array (update) stopping at and not including the element at the integer that was passed
      to the method. It then copies the elements after the index. 
      */
      System.arraycopy(arr,0, update,0,index); //passing the array to copy from, where to start, where we are copying, where to start, where to stop.
      System.arraycopy(arr, index + 1, update, index, arr.length - index - 1);//array copying from, where we're starting, where to copy, where to start, stop.
      return update; //returns the array elements were copied to.
   }//closes method
   

   /*
   This method takes in a string array, a boolean, and a reference to a 2d array. It returns a boolean.
   */
   public static boolean checkString(String[] arr, boolean winner, int[][] available){//opens method
      /*
      Each if statement checks the length of each string in the string array, if it's three then it prints a message and makes winner true
      */
      if (arr[0].length() == 3){
         System.out.println("Winner on row 1");
         winner = true;
      }
      if (arr[1].length() == 3){
         System.out.println("Winner on row 2");
         winner = true;
      }
      if (arr[2].length() == 3){
         System.out.println("Winner on row 3");
         winner = true;
      }
      if (arr[3].length() == 3){
         System.out.println("Winner on column 1");
         winner = true;
      }
      if (arr[4].length() == 3){
         System.out.println("Winner on column 2");
         winner = true;
      }
      if (arr[5].length() == 3){
         System.out.println("Winner on column 3");
         winner = true;
      }
      if (arr[6].length() == 3){
         System.out.println("Winner on diagonal 1");
         winner = true;
      }
      if (arr[7].length() == 3){
         System.out.println("Winner on diagonal 2");
         winner = true;
      }
      
      /*
      The next few lines check if the length of each array in the 2d array is zero, if the arrays are empty, then a message is printed and winner is set 
      to true. This ensures that the program identifies when there are no more boxes to choose from and there is no winner.
      */
      int availableSum = 0; //this initializes a variable to store the sum the length of each array in the array
      
      for(int i = 0;i<available.length;i++){ //this for loop iterates through each array and adds its length to sum
         availableSum += available[i].length;
      }
      
      if((availableSum==0) && (winner == false)){ //if the sum is 0 and if winner is still false, a message is printed and winner is set to true
         System.out.println("No winner");
         winner = true;
      }
 
        
      return winner;  //returns winner    
   
   }//closes method
   
   
   /*
   This method updates a string array using string concatenation. It takes in a string array and an integer and returns nothing
   */
   public static void updateStringArr(String[] arr, int num){//opens method
      /*
      depending on what the integer is it is added to the corresponding string in the array. For example if the number belongs in the first row
      the it is added to the first string and so on. For each number it checks if it belongs to a row, column, and diagonal and added accordingly.
      */
      if (num==1||num==2||num==3){//opens if else statements
         arr[0] += num;
      }else if (num==4||num==5||num==6){
         arr[1] += num;
      }else if (num==7||num==8||num==9){
         arr[2] += num;
      }//closes if else statements
      
      if (num == 1||num==4||num==7){//opens if else statements
         arr[3] += num;
      }else if (num==2||num==5||num==8){
         arr[4]+= num;
      }else if (num == 3||num==6||num==9){
         arr[5] += num;
      }//closes if else statements
      
      if (num == 1||num==5||num==9){
         arr[6]+= num;
      }
      if (num ==3||num==5||num==7){
         arr[7] += num;
      }
   }//closes method
   
   
   /*
   This method takes in a string array, a type string number, and a type string symbol. It prints the board replacing the string number by the symbol.
   */
   public static void updateBoard(String[] arr, String num, String symbol){//opens method
      /*
      Loop iterates through the numbers in the string array, and if a number(string) matches the number(string) passed to method
      it is replaced by the symbol. The for loop prints the array in form of a tic tac toe board.
      */
      for(int i=1; i<=7; i+=3){//opens outer loop
         for (int j =i; j<(i + 3); j++){
            if(arr[j-1].equals(num)){//opens if statement
               arr[j-1] = symbol;
            }//closes if statement
            System.out.print(arr[j-1] + " | ");
         }//closes inner for loop
         System.out.println();
         System.out.println("-----------");
      }//closes outer for loop
    }//closes method
   
 
   /*
   This method takes in nothing and returns an integer. It prompts the user to enter a number.
   */  
   public static int getUserNum(){//opens method
      Scanner input = new Scanner(System.in);//creates reference to object of type Scanner
      System.out.println("Enter a number from the available list: ");
      int num = input.nextInt(); //assigns user's input to num
      return num; //returns num(user's input)
   }//closes method
   
   
   /*
   This method takes in nothing and returns nothing. It prints the original complete board
   */
   public static void printOriginalBoard(){//opens method
      /*
      For loop prints each number from 1-9 in form of a board with three rows and three columns 
      */
      for (int i = 1; i<=7;i+=3){//opens outer loop
         for(int j =i;j<(i+3);j++){//opens inner loop
            System.out.print(j + " | ");
         }//closes inner loop
      System.out.println();
      System.out.println("-----------");
      }//closes outer loop
      System.out.println();
   }//closes method
   
}//closes class