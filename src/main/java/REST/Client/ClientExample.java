package REST.Client;

import REST.Beans.User;
import REST.Beans.Users;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClientExample {

    public static void main(String args[]){
        Client client = Client.create();
        String serverAddress = "http://localhost:1337";
        ClientResponse clientResponse = null;

        // POST EXAMPLE
        String postPath = "/users/add";
        User user = new User("riccardo","presotto");
        clientResponse = postRequest(client,serverAddress+postPath,user);
        System.out.println(clientResponse.toString());

        //GET REQUEST #1
        String getPath = "/users";
        clientResponse = getRequest(client,serverAddress+getPath);
        System.out.println(clientResponse.toString());
        Users users = clientResponse.getEntity(Users.class);
        System.out.println("Users List");
        for (User u : users.getUserslist()){
            System.out.println("Name: " + u.getName() + " Surname: " + u.getSurname());
        }

        //GET REQUEST #2
        String getUserPath = "/users/get/riccardo";
        clientResponse = getRequest(client,serverAddress+getUserPath);
        System.out.println(clientResponse.toString());
        User userResponse = clientResponse.getEntity(User.class);
        System.out.println("Name: " + userResponse.getName() + " Surname: " + userResponse.getSurname());

    }

    public static ClientResponse postRequest(Client client, String url, User u){
        WebResource webResource = client.resource(url);
        String input = new Gson().toJson(u);
        try {
            return webResource.type("application/json").post(ClientResponse.class, input);
        } catch (ClientHandlerException e) {
            System.out.println("Server non disponibile");
            return null;
        }
    }

    public static ClientResponse getRequest(Client client, String url){
        WebResource webResource = client.resource(url);
        try {
            return webResource.type("application/json").get(ClientResponse.class);
        } catch (ClientHandlerException e) {
            System.out.println("Server non disponibile");
            return null;
        }
    }
}
