package htsjdk.samtools.jni;


import java.io.Closeable;
import java.io.IOException;

public interface SamReader extends Closeable{
public SamHeader getHeader();
public long visit(SamRecordHandler handler) throws IOException;
}
