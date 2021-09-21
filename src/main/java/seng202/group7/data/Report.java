package seng202.group7.data;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import javax.naming.directory.InvalidAttributeValueException;
//TODO Not all param's are given descriptions which are needed.
public abstract class Report {
    private LocalDateTime date = null;
    private SimpleStringProperty primaryDescription = new SimpleStringProperty(null);
    private SimpleStringProperty secondaryDescription = new SimpleStringProperty(null);
    private SimpleStringProperty locationDescription = new SimpleStringProperty(null);
    private SimpleObjectProperty<Boolean> domestic = new SimpleObjectProperty<>(null);
    private SimpleObjectProperty<Integer> xCoord = new SimpleObjectProperty<>(null);
    private SimpleObjectProperty<Integer> yCoord = new SimpleObjectProperty<>(null);
    private SimpleObjectProperty<Double> latitude = new SimpleObjectProperty<>(null);
    private SimpleObjectProperty<Double> longitude = new SimpleObjectProperty<>(null);



    /**
     * Sets the data for all attributes in the report
     * @param date
     * @param primaryDescription
     * @param secondaryDescription
     * @param locationDescription
     * @param domestic
     * @param xCoord
     * @param yCoord
     * @param latitude
     * @param longitude
     */
    public Report(LocalDateTime date, String primaryDescription, String secondaryDescription, String locationDescription,
            Boolean domestic, Integer xCoord, Integer yCoord, Double latitude, Double longitude) {
        this.date = date;
        this.primaryDescription = new SimpleStringProperty(primaryDescription);
        this.secondaryDescription = new SimpleStringProperty(secondaryDescription);
        this.locationDescription.setValue(locationDescription);
        this.domestic.setValue(domestic);
        this.xCoord.setValue(xCoord);
        this.yCoord.setValue(yCoord);
        this.latitude.setValue(latitude);
        this.longitude.setValue(longitude);
    }

    /**
     * Updates the values found in the report object.
     * @param date
     * @param primaryDescription
     * @param secondaryDescription
     * @param locationDescription
     * @param domestic
     * @param xCoord
     * @param yCoord
     * @param latitude
     * @param longitude
     * @throws InvalidAttributeValueException
     */
    public void update(LocalDateTime date, String primaryDescription, String secondaryDescription, String locationDescription,
            Boolean domestic, Integer xCoord, Integer yCoord, Double latitude, Double longitude) throws InvalidAttributeValueException {
        setDate(date);
        setPrimaryDescription(primaryDescription);
        setSecondaryDescription(secondaryDescription);
        setLocationDescription(locationDescription);
        setDomestic(domestic);
        setXCoord(xCoord);
        setYCoord(yCoord);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    /**
     * 
     * @return An ArrayList containing what types of attributes are in the report
     */
    public abstract ArrayList<String> getSchema();
    
    /**
     * @return An ArrayList containing all of the report's attributes
     */
    public abstract ArrayList<String> getAttributes();
    
    /**
     * 
     * @return The date the report was submitted
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Sets the date the report was submitted
     * @param date A required LocalDateTime attribute which must be unique
     * @throws InvalidAttributeValueException If the attribute is null
     */
    public void setDate(LocalDateTime date) throws InvalidAttributeValueException {
        if (date == null) {
            throw new InvalidAttributeValueException("Date cannot be null");
        } if (!Objects.equals(getDate(), date)) {
            this.date = date;
        }
    }

    /**
     * 
     * @return The primary description of the report
     */
    public String getPrimaryDescription() {
        return this.primaryDescription.get();
    }


    /**
     * Sets the primary description of the report
     * @param primaryDescription A required String attribute which must be unique
     * @throws InvalidAttributeValueException If the attribute is null
     */
    public void setPrimaryDescription(String primaryDescription) throws InvalidAttributeValueException {
        if (Objects.equals(primaryDescription, "") || (primaryDescription == null)) {
            throw new InvalidAttributeValueException("Primary description cannot be null");
        } else if (!Objects.equals(getPrimaryDescription(), primaryDescription)) {
            this.primaryDescription.setValue(primaryDescription);
        }
    }

    /**
     * 
     * @return The secondary description of the report
     */
    public String getSecondaryDescription() {
        return this.secondaryDescription.get();
    }
    

    /**
     * Sets the secondary description of the report
     * @param secondaryDescription A required String attribute which must be unique
     * @throws InvalidAttributeValueException If the attribute is null
     */
    public void setSecondaryDescription(String secondaryDescription) throws InvalidAttributeValueException {
        if (Objects.equals(secondaryDescription, "") || (secondaryDescription == null)) {
            throw new InvalidAttributeValueException("Secondary description cannot be null");
        } else if (!Objects.equals(getSecondaryDescription(), secondaryDescription)){
            this.secondaryDescription.setValue(secondaryDescription);
        }
    }

    /**
     * 
     * @return The description of the location for the report
     */
    public String getLocationDescription() {
        return this.locationDescription.get();
    }

    /**
     * Set the locations description for the report
     * @param locationDescription
     */
    public void setLocationDescription(String locationDescription) {
        if (Objects.equals(locationDescription, "")) {
            this.locationDescription.setValue(null);
        } else if (!Objects.equals(getLocationDescription(), locationDescription)) {
            this.locationDescription.setValue(locationDescription);
        }
    }

    /**
     * 
     * @return If the report was domestic
     */
    public Boolean getDomestic() {
        return this.domestic.get();
    }

    /**
     * Sets if the report was domestic
     * @param domestic
     */
    public void setDomestic(Boolean domestic) {
        if (!Objects.equals(getDomestic(), domestic)) {
            this.domestic.setValue(domestic);
        }
    }

    /**
     * 
     * @return The x-coordinate for the location the report took place
     */
    public Integer getXCoord() {
        return this.xCoord.get();
    }

    /**
     * Set the x-coordinate of where the report took place
     * @param xCoord
     */
    public void setXCoord(Integer xCoord) {
        if (!Objects.equals(getXCoord(), xCoord)) {
            this.xCoord.setValue(xCoord);
        }
    }

    /**
     * 
     * @return The y-coordinate for the location the report took place
     */
    public Integer getYCoord() {
        return this.yCoord.get();
    }


    /**
     * Set the y-coordinate of where the report took place
     * @param yCoord
     */
    public void setYCoord(Integer yCoord) {
        if (!Objects.equals(getYCoord(), yCoord)) {
            this.yCoord.setValue(yCoord);
        }
    }

    /**
     *  
     * @return The geographical latitude of where the report took place
     */
    public Double getLatitude() {
        return this.latitude.get();
    }


    /**
     * Set the geographical latitude of where the report took place
     * @param latitude
     */
    public void setLatitude(Double latitude) {
        if (!Objects.equals(getLatitude(), latitude)) {
            this.latitude.setValue(latitude);
        }
    }

    /**
     *  
     * @return The geographical longitude of where the report took place
     */
    public Double getLongitude() {
        return this.longitude.get();
    }


    /**
     * Set the geographical longitude of where the report took place
     * @param Longitude
     */
    public void setLongitude(Double longitude) {
        if (!Objects.equals(getLongitude(), longitude))
        this.longitude.setValue(longitude);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(date, report.getDate())
            && Objects.equals(primaryDescription.get(), report.getPrimaryDescription())
            && Objects.equals(secondaryDescription.get(), report.getSecondaryDescription())
            && Objects.equals(locationDescription.get(), report.getLocationDescription())
            && Objects.equals(domestic.get(), report.getDomestic())
            && Objects.equals(xCoord.get(), report.getXCoord())
            && Objects.equals(yCoord.get(), report.getYCoord())
            && Objects.equals(latitude.get(), report.getLatitude())
            && Objects.equals(longitude.get(), report.getLongitude());
    }


    @Override
    public String toString() {
        return ", date=" + date +
                ", primaryDescription='" + getPrimaryDescription() + '\'' +
                ", secondaryDescription='" + getSecondaryDescription() + '\'' +
                ", locationDescription='" + getLocationDescription() + '\'' +
                ", domestic=" + getDomestic() +
                ", xCoord=" + getXCoord() +
                ", yCoord=" + getYCoord() +
                ", latitude=" + getLatitude() +
                ", longitude=" + getLongitude()
                ;
    }
}