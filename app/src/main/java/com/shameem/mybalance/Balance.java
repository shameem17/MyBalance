package com.shameem.mybalance;

class Balance {
   String balance;
   String source;
   String updatedAt;

   private static volatile Balance instance = null;
   public Balance(){

   }
   private Balance(String balance, String source, String updatedAt) {
      this.balance = balance;
      this.source = source;
      this.updatedAt = updatedAt;
   }

   public static synchronized Balance getInstance(){
      if (instance == null){
         instance = new Balance();
      }
      return instance;
   }
}
