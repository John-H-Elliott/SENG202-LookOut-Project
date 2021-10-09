package seng202.group7.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * A class used to serialize objects and store them in a file, and to deserialize them back into objects
 * @author Shaylin Simadari
 */
public final class Serializer {
    /**
     * Serializes a FilterConditions object into a file
     * @param file The file to write the FilterConditions object to
     * @throws CustomException
     */
    public static void serialize(File file, FilterConditions object) throws CustomException {
        try (FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut)) {
            outputStream.writeObject(object);
        } catch (IOException e) {
            throw new CustomException("Failed to write filter into a file.", e.getMessage());
        }
    }

    /**
     * Deserializes a FilterConditions object from a file
     * @param file The file from which to get the FilterConditions object
     * @throws CustomException
     */
    public static FilterConditions deserialize(File file) throws CustomException{
        FilterConditions typedObj = null;
        try (FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fileIn)) {
            Object object = inputStream.readObject();

            typedObj = (FilterConditions) object;

        } catch (IOException | ClassNotFoundException e) {
            throw new CustomException("Failed to read filter from selected file.", e.getMessage());
        }
        return typedObj;
    }
}
