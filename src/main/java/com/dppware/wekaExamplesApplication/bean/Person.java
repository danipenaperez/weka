package com.dppware.wekaExamplesApplication.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Person {
    /**
     * @ATTRIBUTE genre {man, woman}
     */
    private String genre;
    /**
     * @ATTRIBUTE hairColour {blode,red,brown,nohair}
     */
    private String hairColour;
    /**
     * @ATTRIBUTE isSpanish {yes,no}
     */
    private Boolean isSpanish;
    /**
     * @ATTRIBUTE profession {actor,sport}
     */
    private String profession;
    /**
     * Image
     */
    private String imageURL;
    /**
     * The name
     */
    private String name;
    /**
     * If has been chossen by the user
     */
    private Boolean choosed;

    public Person(String genre, String hairColour, Boolean isSpanish, String profession, String imageURL, String name) {
        super();
        this.genre = genre;
        this.hairColour = hairColour;
        this.isSpanish = isSpanish;
        this.profession = profession;
        this.imageURL = imageURL;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        boolean eq = false;
        if (obj instanceof Person) {
            Person op = (Person) obj;
            eq = this.getName().equals(op.getName());
        }

        return eq;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
