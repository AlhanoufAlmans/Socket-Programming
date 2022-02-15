/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs330;


import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alhanoufalmansour
 */
public class Client {
     public static void main(String[] args){ 
         try{
        Socket socket = new Socket ("192.168.56.1",6666);
        Scanner input = new Scanner(System.in);
        Scanner inputt = new Scanner(System.in);
        System.out.println("enter 1 for non-encrypted/n or 2 for encrypted: 3 to quit");
         Integer ch= inputt.nextInt();
         String choice;
        if(ch == 1)
        {
          DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());//send to client 
          System.out.println("please enter the message!");
          String clientMessage = input.nextLine();
////          //======="sending choice + string"========================
            choice = ch.toString();
            clientMessage= choice+clientMessage;
          dataOutputStream.writeBytes(clientMessage + '\n'); 
////          //=============================================
          //recieve
          InputStreamReader inputStreamReader =  new InputStreamReader(socket.getInputStream()); 
          BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
          String text = bufferedReader.readLine();
          System.out.println("The server's reply: "+ text + '\n');
          

        
         }        
        
        else if(ch==2){
             choice = ch.toString();
             //PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
             System.out.println("please enter the message!");
             String message = input.nextLine();
             InputStreamReader inputStreamReader =  new InputStreamReader(socket.getInputStream());
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
 
             String key = "31452";
           /* columnCount would keep track of columns */
      int columnCount = key.length();

      /* rowCount will keep of track of rows...no of rows = (plaintextlength + keylength) / keylength */
      int rowCount = (message.length()+columnCount)/columnCount;

      /*plainText and cipherText would be array containing ASCII values for respective alphabets */
      int plainText[][] = new int[rowCount][columnCount];
      int cipherText[][] = new int[rowCount][columnCount];
 
      /*Encryption Process*/
      System.out.print("\n-----Encryption-----\n"); 
      cipherText = encrypt(plainText, cipherText, message, rowCount, columnCount, key);
 
      // prepare final string
      String ct = "";
      for(int i=0; i<columnCount; i++)
      {
           for(int j=0; j<rowCount; j++)
           {
                 if(cipherText[j][i] == 0)
                      ct = ct + 'x';
                 else{
                      ct = ct + (char)cipherText[j][i];
                 }
           }
      }
      System.out.print("\nCipher Text: " + ct);
      /*Decryption Process*/
      System.out.print("\n\n\n-----Decryption-----\n");
 
      plainText = decrypt(plainText, cipherText, ct, rowCount, columnCount, key);
 
      // prepare final string
      String pt = "";
      for(int i=0; i<rowCount; i++)
      {
            for(int j=0; j<columnCount; j++)
            {
                 if(plainText[i][j] == 0)
                       pt = pt + "";
                 else{
                        pt = pt + (char)plainText[i][j];
                 }
            }
      }
      System.out.print("\nPlain Text: " + pt);
      
      System.out.println();
 //          =========="sending choice + string"=========================
            choice = ch.toString();
            ct= choice+ct;
            dataOutputStream.writeBytes(ct + '\n'); 
//          =============================================================
           // recieve
          System.out.println("The server's reply: "+ ct);
             
       }
        if(ch==3){
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//          //=======sending choice========================
            choice = ch.toString();
          dataOutputStream.writeBytes(choice + '\n');
//          //=============================================
         socket.close();
        }
     
           }
         catch(IOException e){
                e.printStackTrace(); 
          }

     } 
     

     static int[][] encrypt(int plainText[][], int cipherText[][], String message, int rowCount, int                                                                                                           columnCount, String key){
           int i,j;
           int k=0;

           /* here array would be filled row by row  */
           for(i=0; i<rowCount; i++)
          {
                for(j=0; j<columnCount; j++)
                {
                      /* terminating condition...as string length can be smaller than 2-D array */
                      if(k < message.length())
                      {
                             /* respective ASCII characters would be placed */
                             plainText[i][j] = (int)message.charAt(k);
                             k++;
                      }
                      else
                      {
                             break;
                      }
                }
          }

          /* here array would be filled according to the key column by column */
          for(i=0; i<columnCount; i++)
          {
                /* currentCol would have current column number i.e. to be read...as there would be ASCII value stored in key so we would subtract it by 48 so that we can get the original number...and -1 would be subtract as array position starts from 0*/
 int currentCol= ( (int)key.charAt(i) - 48 ) -1;
                for(j=0; j<rowCount; j++)
                {
                      cipherText[j][i] = plainText[j][currentCol];
                }
 
          }

          System.out.print("Cipher Array(read column by column): \n");
          for(i=0;i<rowCount;i++){
                for(j=0;j<columnCount;j++){
                      System.out.print((char)cipherText[i][j]+"\t");
                }
                System.out.println();
          }

          return cipherText;
     }
         static int[][] decrypt(int plainText[][], int cipherText[][], String message, int rowCount, int columnCount, String key){
            int i,j;
            int k=0;

            for(i=0; i<columnCount; i++)
            {
                  int currentCol= ( (int)key.charAt(i) - 48 ) -1;
                  for(j=0; j<rowCount; j++)
                  {
                        plainText[j][currentCol] = cipherText[j][i];
                  } 
            }

            System.out.print("Plain Array(read row by row): \n");
            for(i=0;i<rowCount;i++){
                 for(j=0;j<columnCount;j++){
                        System.out.print((char)plainText[i][j]+"\t");
                 }
                 System.out.println();
            }

           return plainText;
  }
}


    