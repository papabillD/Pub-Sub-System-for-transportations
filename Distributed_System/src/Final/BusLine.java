package Final;

import java.io.Serializable;

public class BusLine implements Serializable {

    private static final long serialVersionUID= -544771288149488854L;
    String lineCode,lineId,descriptionEnglish;

    public BusLine(String lineCode, String lineId, String descriptionEnglish) {
        this.lineCode = lineCode;
        this.lineId = lineId;
        this.descriptionEnglish = descriptionEnglish;
    }

    public String getLineCode() {
        return lineCode;
    }

    public String getLineId() {
        return lineId;
    }

    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    @Override
    public String toString() {
        return "BusLine{" +
                "lineCode='" + lineCode + '\'' +
                ", lineId='" + lineId + '\'' +
                ", descriptionEnglish='" + descriptionEnglish + '\'' +
                '}';
    }
}
