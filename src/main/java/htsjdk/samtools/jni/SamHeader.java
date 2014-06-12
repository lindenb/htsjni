package htsjdk.samtools.jni;

public interface SamHeader {
public int getReferenceCount();
public String getReferenceName(int index);
public int getReferenceLength(int index);
}
