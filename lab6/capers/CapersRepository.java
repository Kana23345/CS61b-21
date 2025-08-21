package capers;

import java.io.File;
import java.io.IOException;


/** A repository for Capers
 * @author TODO
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    // TODO Hint: look at the `join`
    //      function in Utils
    //      function in Utils
    static final File CAPERS_FOLDER_PATH = Utils.join(CWD,".capers");
    static final File DOG_FOLDER =Utils.join(".capers","dogs") ;
    static final File STORY_FILE =Utils.join(".capers","story");

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() throws IOException {
        // TODO
        if(!CAPERS_FOLDER_PATH.exists()){
            CAPERS_FOLDER_PATH.mkdir();
        }
        if(!DOG_FOLDER.exists()){
            DOG_FOLDER.mkdir();
        }
        if (!STORY_FILE.exists()){
            STORY_FILE.createNewFile();
        }


    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        // TODO
        String currentText=Utils.readContentsAsString(STORY_FILE);
        if (currentText.isEmpty()){
            currentText=text;
        }
        else {
            currentText=currentText.concat("\n"+text);
        }
        Utils.writeContents(STORY_FILE,currentText);
        System.out.print(currentText);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) throws IOException {
        // TODO
        Dog dog=new Dog(name,breed,age);
        dog.saveDog();
        System.out.println(dog.toString());
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) throws IOException {
        // TODO
        Dog dog =Dog.fromFile(name);
        dog.haveBirthday();

    }
}
