// Made by Sonu kumar
package restAssured;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class PojoResponse implements Serializable

{
    @SerializedName("padding")
    @Expose
    private String padding;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("expiresAt")
    @Expose
    private String expiresAt;
    @SerializedName("expiresAtTimestamp")
    @Expose
    private Integer expiresAtTimestamp;
    @SerializedName("longExpiresAt")
    @Expose
    private String longExpiresAt;
    @SerializedName("longExpiresAtTimestamp")
    @Expose
    private Integer longExpiresAtTimestamp;
    private final static long serialVersionUID = 2357549654267921812L;

    /**
     * No args constructor for use in serialization
     *
     */

    public PojoResponse() {
    }

    /**
     *
     * @param padding
     * @param longExpiresAt
     * @param code
     * @param longExpiresAtTimestamp
     * @param expiresAtTimestamp
     * @param uuid
     * @param expiresAt
     */
    public PojoResponse(String padding, String uuid, String code, String expiresAt, Integer expiresAtTimestamp, String longExpiresAt, Integer longExpiresAtTimestamp) {
        super();
        this.padding = padding;
        this.uuid = uuid;
        this.code = code;
        this.expiresAt = expiresAt;
        this.expiresAtTimestamp = expiresAtTimestamp;
        this.longExpiresAt = longExpiresAt;
        this.longExpiresAtTimestamp = longExpiresAtTimestamp;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Integer getExpiresAtTimestamp() {
        return expiresAtTimestamp;
    }

    public void setExpiresAtTimestamp(Integer expiresAtTimestamp) {
        this.expiresAtTimestamp = expiresAtTimestamp;
    }

    public String getLongExpiresAt() {
        return longExpiresAt;
    }

    public void setLongExpiresAt(String longExpiresAt) {
        this.longExpiresAt = longExpiresAt;
    }

    public Integer getLongExpiresAtTimestamp() {
        return longExpiresAtTimestamp;
    }

    public void setLongExpiresAtTimestamp(Integer longExpiresAtTimestamp) {
        this.longExpiresAtTimestamp = longExpiresAtTimestamp;
    }

}
