package boggle;
import java.io.IOException;
import java.util.Collection;
import java.util.Random;


public class TextDriver
{

    /**
     * @param args
     */
    public static void main(String[] args)
    throws IOException
    {
        Boggle boggle = new Boggle("words.txt");
        Random r = new Random(299);
        boggle.configureBoard(r);
        System.out.println(boggle);
        // Find all the words that this boggle board can generate.
        Collection<String> words = boggle.findValidWords();
        System.out.printf("Found %d valid words\n", words.size());
        for (String s : words) {
            System.out.println(s);
        }
    }

}
