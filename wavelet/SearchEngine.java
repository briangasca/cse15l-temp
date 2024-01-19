import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String[] added = new String[100];
    int num_added = 0;
    String string = "Nothing Searched Yet.";

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Searched:", string);
        } else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if(parameters[0].equals("s")){
                added[0] = parameters[1];
                num_added++;
                string = parameters[1];
                return String.format("Added %s!", parameters[1]);
            }
            
        } else if (url.getPath().equals("/search")){
            String[] parameters = url.getQuery().split("=");
            if(parameters[0].equals("s")){
                for(int i = 0; i < num_added; i++){
                    if(parameters[1].equals(added[i])){
                        return String.format("Found %s!", parameters[1]);
                    }
                }
                return String.format("Couldn't find %s, did you add it?", parameters[1]);
            }
        }
        return "404 Not Found.";
    }
}

class NumberServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
