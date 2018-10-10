package com.example.mac.udacitycapstonefinalproject.Service;

import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;
import com.example.mac.udacitycapstonefinalproject.Model.Users;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SvAutomoviles {

    public Automoviles GetAutomovil(DataSnapshot dataSnapshot) {
        Automoviles automoviles =  dataSnapshot.getValue(Automoviles.class);
        return automoviles;
    }
    public List<Automoviles> GetAutomoviles(DataSnapshot dataSnapshot) {
        /**
         * Call of the new arraylist notice that it is a Automoviles class List
         * You can set from List and convert it to an arraylist not the other way around
         */
        List<Automoviles> automovilesList = new ArrayList<>();
        /**
         * foreach Loop to iterate though the different ids of Automoviles objects present
         * in the firebase database
         */
        for(DataSnapshot ds:dataSnapshot.getChildren()) {
            /**
             * Create an automoviles Object and assign all the data from the database
             */
            Automoviles automoviles = ds.getValue(Automoviles.class);
            /**
             * Assign the automoviles object into the arraylist!
             */
            automovilesList.add(automoviles);
        }
        /**
         * This way you can see that the return value of the method is an Arraylist<Automoviles>
         *     Then when you call GetAutomoviles method you will pass in the parameter it needs
         *     and you will have your List.
         */
        return automovilesList;
    }

    public List<Users> getUsers(DataSnapshot snapshot){

        List<Users> usersList=new ArrayList<>();

        for(DataSnapshot ds:snapshot.getChildren()) {
            /**
             * Create an automoviles Object and assign all the data from the database
             */
            Users users = ds.getValue(Users.class);

            usersList.add(users);


    }
        return usersList;


    }
}

