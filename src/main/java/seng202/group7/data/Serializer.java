package seng202.group7.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.invoke.TypeDescriptor;

/**
 * A class used to serialize objects and store them in a file, and to deserialize them back into objects
 * @author Shaylin Simadari
 */
public final class Serializer {
    /**
     * Serializes a FilterConditions object into a file
     * @param file The file to write the FilterConditions object to
     */
    public static <T extends Serializable> void serialize(File file, T object){
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(object);
            outputStream.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes a FilterConditions object from a file
     * @param file The file from which to get the FilterConditions object
     */
    public static <T extends Serializable> T deserialize(File file){
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fileIn);
            T typedObj = null;
            Object object = inputStream.readObject();
            try {
                typedObj = (T)object;
            } catch (ClassCastException e) {
                System.out.println("Serializer error: " + e);
            }
            inputStream.close();
            fileIn.close();
            return typedObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
