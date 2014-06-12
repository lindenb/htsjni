package htsjdk.samtools.jni;

public interface SamRecordHandler {
public int handleSamRecord(final SamReader reader,final SamRecord rec);
}
