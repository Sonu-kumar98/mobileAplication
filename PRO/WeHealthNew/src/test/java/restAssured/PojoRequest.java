// Made by Sonu kumar
package restAssured;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class PojoRequest implements Serializable {
    @SerializedName("symptomDate")
    @Expose
    private String symptomDate;
    @SerializedName("testDate")
    @Expose
    private String testDate;
    @SerializedName("testType")
    @Expose
    private String testType;
    @SerializedName("tzOffset")
    @Expose
    private Integer tzOffset;
    @SerializedName("padding")
    @Expose
    private String padding;
    private final static long serialVersionUID = 4777993796624412831L;

    /**
     * No args constructor for use in serialization
     */
    public PojoRequest() {
    }
    /**
     * @param symptomDate
     * @param padding
     * @param tzOffset
     * @param testType
     * @param testDate
     */
    public PojoRequest(String symptomDate, String testDate, String testType, Integer tzOffset, String padding) {
        super();
        this.symptomDate = symptomDate;
        this.testDate = testDate;
        this.testType = testType;
        this.tzOffset = tzOffset;
        this.padding = padding;
    }

    public String getSymptomDate() {
        return symptomDate;
    }

    public void setSymptomDate(String symptomDate) {
        this.symptomDate = symptomDate;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public Integer getTzOffset() {
        return tzOffset;
    }

    public void setTzOffset(Integer tzOffset) {
        this.tzOffset = tzOffset;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }
}

