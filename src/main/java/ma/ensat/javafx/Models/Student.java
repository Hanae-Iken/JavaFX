package ma.ensat.javafx.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Student {
        private String id;
        private String name;
        private float moyenne;
        private Integer formation_id;

        public void setMoyenne(float moyenne) {
            if (moyenne < 0 || moyenne > 20) {
                throw new IllegalArgumentException("Moyenne doit etre entre 0 and 20");
            }
            this.moyenne = moyenne;
        }
    }

