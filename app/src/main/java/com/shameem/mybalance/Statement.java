package com.shameem.mybalance;

class Statement {

   String amount;
   String balanceAfter;
   String balanceBefore;
   String time;
   String transactionType;

   private static volatile Statement instance = null;
   public  Statement(){

   }
   private Statement(String amount, String balanceAfter, String balanceBefore, String time, String transactionType) {
      this.amount = amount;
      this.balanceAfter = balanceAfter;
      this.balanceBefore = balanceBefore;
      this.time = time;
      this.transactionType = transactionType;
   }

   public static synchronized Statement getInstance(){
      if (instance == null){
         instance = new Statement();
      }
      return instance;
   }
}
