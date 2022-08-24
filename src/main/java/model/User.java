package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor


    public class User {
        private int id;
        private String name;
        private String surname;
        private String email;

        private int eventid;

        public User(String name, String surname, String email, int eventid) {
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.eventid = eventid;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", email='" + email + '\'' +
                    ", eventid=" + eventid +
                    '}';
        }
    }
