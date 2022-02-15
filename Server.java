/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs330;
import java.io.*;
import java.net.*;
/**
 *
 * @author alhanoufalmansour
 */
public class Server {
        public static void main (String[] args){
            try{
                ServerSocket serverSocket = new ServerSocket(6666);
                System.out.println("waiting for client to connect..."); 
                Socket socket = serverSocket.accept(); //block
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
//                isEqualTo()
                String clientmessage = bufferedReader.readLine();
                
                DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
                outToClient.writeBytes("Done" + '\n');
                String choice = clientmessage.substring(0,1);
                if (choice.equals("3")){
                    System.out.println("communication with client is closed");
                    socket.close();
                }
                else{
                clientmessage= clientmessage.substring(1);
                System.out.println("the client' message is "+clientmessage);}
                
            }
            catch(IOException e){
                e.printStackTrace();
        }
        
}
      
}

