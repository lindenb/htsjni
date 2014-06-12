package htsjdk.samtools.jni;

import htsjdk.samtools.SAMRecord;
import htsjdk.samtools.SAMRecordIterator;
import htsjdk.samtools.ValidationStringency;

import java.io.File;
import java.security.MessageDigest;

public class SamReaderFactoryTest
	
	{
	private File bamFile=new File("/home/lindenb/package/samtools/test/mpileup/mpileup.1.bam");
	private static int N_LOOP=5000;
	private String digest(MessageDigest md)
		{
 
        byte[] mdbytes = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
 
       return  sb.toString();
		}
	
	private void scanNative() throws Exception
		{
		long start=System.currentTimeMillis();
        final MessageDigest md = MessageDigest.getInstance("MD5");
		for(int N=0;N< N_LOOP;++N)
			{
			SamReaderFactory f=SamReaderFactory.newInstance();
			
			SamReader r=f.open( bamFile);
			
			r.visit(new SamRecordHandler() {
				@Override
				public int handleSamRecord(SamReader reader, SamRecord rec) {
				md.digest((rec.getReadName()+rec.getReadString()+rec.getBaseQualityString()+rec.getAlignmentStart()).getBytes());
				return 0;
				}
				});
			r.close();
			}
		System.out.println("Native\t\t\tmd5="+digest(md)+" time="+(System.currentTimeMillis()-start));
		}
	
	private void scanPureJava() throws Exception
		{
		long start=System.currentTimeMillis();
	    final MessageDigest md = MessageDigest.getInstance("MD5");
		for(int N=0;N< N_LOOP;++N)
			{
			htsjdk.samtools.SamReader r=htsjdk.samtools.SamReaderFactory.makeDefault().
					validationStringency(ValidationStringency.DEFAULT_STRINGENCY).
					open(bamFile);
			SAMRecordIterator iter=r.iterator();
			while(iter.hasNext())
				{
				SAMRecord rec=iter.next();
				md.digest((rec.getReadName()+rec.getReadString()+rec.getBaseQualityString()+rec.getAlignmentStart()).getBytes());
				}
				
			r.close();
			}
		System.out.println("PureJava\t\t\tmd5="+digest(md)+" time="+(System.currentTimeMillis()-start));
		}


	
	public static void main(String[] args) throws Exception{
		SamReaderFactoryTest app=new SamReaderFactoryTest();
		app.scanNative();
		app.scanPureJava();
		System.out.println("Done");
		}

	}
