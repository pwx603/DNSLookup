package ca.ubc.cs.cs317.dnslookup;
/** Record types supported by the application. Includes a few common record types that are not
 * fully supported by this application.
 */
public enum RecordClass {
    IN(1), CS(2), CH(3), HS(4), OTHER(0);

    private int code;

    RecordClass(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /** Returns the record type associated to a particular code, or OTHER if no known record
     * type is linked to that code.
     *
     * @param code The record type code to be searched.
     * @return A record type that uses the specified code, or OTHER if no record type uses the code.
     */
    public static RecordClass getByCode(int code) {
        for (RecordClass rs : values())
            if (rs.code == code)
                return rs;
        return OTHER;
    }
}
