package marvel;
import com.opencsv.bean.CsvBindByName;

public class MarvelModel {

    //the name of the object
    @CsvBindByName
    private String hero;

    //they name of where they are located
    @CsvBindByName
    private String book;

    //returns the name of the object
    public String getHeroName(){ return hero; }

    //takes in a string and changes the name of the object to the value
    public void setName(String v) {
        hero = v;
    }

    //returns the name of where it is located
    public String getBook() {
        return book;
    }

    // takes in a string and changes the name of where it is located
    public void setBook(String v) {
        book = v;
    }

}
