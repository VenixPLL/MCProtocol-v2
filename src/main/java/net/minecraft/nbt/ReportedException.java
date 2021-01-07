package net.minecraft.nbt;

public class ReportedException extends RuntimeException {
    private final Exception theReportedExceptionCrashReport;

    public ReportedException(Exception exception)
    {
        this.theReportedExceptionCrashReport = exception;
    }

    public Throwable getCause()
    {
        return theReportedExceptionCrashReport.getCause();
    }

    public String getMessage()
    {
        return theReportedExceptionCrashReport.getMessage();
    }
}