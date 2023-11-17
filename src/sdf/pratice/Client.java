package sdf.pratice;

import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        int port = 3000;
      String localhost = "";
      switch (args.length) {
         case 1:
            localhost = args[0];
            break;
         case 2:
            localhost = args[0];
            port = Integer.parseInt(args[1]);
            break;
         default:
            port = Integer.parseInt(args[0]);
      }
      System.out.println("Connected");

       Socket socket = new Socket();
       Program prog = new Program(socket);

     

    
}
}