package exception;

/**
 * Created by aude on 2016/12/21.
 */
public class AudeException extends RuntimeException {

    private static final long serialVersionID = -378533410763590157L;
    private TraceContext trace;

    public AudeException(String message) {
        super(message);
    }

    public AudeException(String message, Exception e) {
        super(message, e);
    }

    public AudeException(String message, Exception e, String sql) {
        this(message, e);
        trace = new TraceContext(sql);
    }

    public AudeException(String message, Exception e, TraceContext trace) {
        this(message, e);
        this.trace = trace;
    }

    @Override
    public String getMessage() {
        return super.getMessage()+(trace!=null?trace.toString():"");
    }
}
