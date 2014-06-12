package htsjdk.samtools.jni;

public interface SamRecord {
public String getReadName();
public int getReferenceIndex();
public int getFlags();
public int getAlignmentStart();
public int getReadLength();
public char getReadBaseAt(int index);
public int getReadQualAt(int index);
public int getMappingQuality();
public boolean hasFlag(int f);
public boolean isPaired();
public boolean isReadMapped();
public boolean isMateMapped();
public int getMateReferenceIndex();
public int getMateAlignmentStart();
public String getReadString();
public int getInferredInsertSize();
public String getBaseQualityString();
public int getCigarNumElements();
public char getCigarOperator(int index);
public int getCigarLength(int index);
public String getCigarString();
}
