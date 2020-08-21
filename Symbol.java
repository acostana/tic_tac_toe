//Author: Ana Acosta
//Date: 12.02.19
//File: Symbol.java

public class Symbol{//opens class

   private String mark;
   
   public void setSymbol(String character){ //sets whatever string is passed to it as the mark
      mark = character;
   }
   
   public String getSymbol(){ //returns the mark
      return mark;
   }
   
   public Symbol(String character){ //prints a message and the string that's passed to it
      System.out.println("Your symbol will be " + character);
   }

}//closes class