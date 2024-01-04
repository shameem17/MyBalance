package com.shameem.mybalance;

public class Statement {

   String amount;
   String balanceAfter;
   String balanceBefore;
   String time;
   String transactionType;

   public Statement(String amount, String balanceAfter, String balanceBefore, String time, String transactionType) {
      this.amount = amount;
      this.balanceAfter = balanceAfter;
      this.balanceBefore = balanceBefore;
      this.time = time;
      this.transactionType = transactionType;
   }
}
