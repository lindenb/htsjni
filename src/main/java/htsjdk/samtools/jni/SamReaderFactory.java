package htsjdk.samtools.jni;


import java.io.File;
import java.io.IOException;

public class SamReaderFactory
	{
	private SamReaderFactory()
		{}
	
	public static SamReaderFactory newInstance()
		{
		HtsJniLibLoader.init();
		return new SamReaderFactory();
		}
	
	public SamReader open(File file) throws IOException
		{
		CWrapper_p_bamFile fp=HtsJniBindings.bam_open(file.getPath(), "r");
		if(fp.isNull()) throw new IOException("Cannot open "+file);
		CWrapper_p_bam_hdr_t header=HtsJniBindings.bam_hdr_read(fp);
		if(header.isNull())
			{
			HtsJniBindings.bam_close(fp);
			throw new IOException("Cannot open "+file);
			}
		return new BamReaderImpl(fp,header);
		}
	
	private static class BamReaderImpl
		implements SamReader
		{
		private CWrapper_p_bamFile samFile;
		private SamHeaderImpl header;
		public BamReaderImpl(CWrapper_p_bamFile samFile,CWrapper_p_bam_hdr_t header)
			{
			this.samFile=samFile;
			this.header=new SamHeaderImpl(header);
			}
		
		public SamHeader getHeader()
			{
			return this.header;
			}
		
		@Override
		public long visit(SamRecordHandler handler)
			throws IOException
			{
			long n=0;
			SamRecordImpl rec=null;
			try
				{
				rec=new SamRecordImpl();
				while(HtsJniBindings.bam_read1(this.samFile, rec.b)>=0)
					{
					++n;
					if(handler!=null && handler.handleSamRecord(this, rec)!=0)
						{
						break;
						}
					}
				return n;
				}
			catch(Throwable err)
				{
				throw new IOException(err);
				}
			finally
				{	
			
				}
			}
		
		@Override
		protected void finalize() throws Throwable {
			this.close();
			super.finalize();
			}
		
		@Override
		public void close() throws IOException {
			if(!this.samFile.isNull())
				{
				HtsJniBindings.bam_close(this.samFile);
				this.samFile.free();
				}
			}
		}
	
	private static class SamHeaderImpl
		implements SamHeader
		{
		private CWrapper_p_bam_hdr_t header;
		
		SamHeaderImpl(CWrapper_p_bam_hdr_t header)
			{
			this.header=header;
			}
		
		public int getReferenceCount()
			{
			return HtsJniBindings.bam_hdr_count_references(header);
			}
		public String getReferenceName(int index)
			{
			return HtsJniBindings.bam_hdr_get_reference_name(header, index);
			}
		public int getReferenceLength(int index)
			{
			return HtsJniBindings.bam_hdr_get_reference_length(header, index);
			}

		}
	
	private static class SamRecordImpl
	implements SamRecord
		{
		private CWrapper_p_bam1_t b;
		private boolean owner;
		
		SamRecordImpl(CWrapper_p_bam1_t b, boolean owner)
			{
			this.b=b;
			this.owner=owner;
			}
		public SamRecordImpl() {
			this(HtsJniBindings.bam_init1(),true);
			}
		@Override
		public boolean hasFlag(int f)
			{
			return (getFlags() & 4)==0;
			}		
		@Override
		public boolean isPaired()
			{
			return hasFlag(1);
			}
		@Override
		public boolean isReadMapped()
			{
			return hasFlag(4);
			}
		@Override
		public boolean isMateMapped()
			{
			return isPaired() && hasFlag(8);
			}
		
		
		
		@Override
		protected void finalize() throws Throwable {
			if(!b.isNull())
				{
				if(this.owner)
					{
					HtsJniBindings.bam_destroy1(b);
					this.b.free();
					}
				}
			super.finalize();
			}
		
		@Override
		public int getReferenceIndex() {
			return HtsJniBindings.my_bam_get_reference_index(b);
			}
		
		public String getReadName()
			{
			return HtsJniBindings.bam_get_qname(b);
			}
		@Override
		public int getAlignmentStart() {
			return HtsJniBindings.my_bam_get_alignment_start(b);
			}
		@Override
		public int getMateReferenceIndex()
			{
			if(!isPaired()) return -1;
			return HtsJniBindings.my_bam_get_mate_reference_index(b);
			}
		@Override
		public int getMateAlignmentStart()
			{
			if(!isPaired()) return 0;
			return HtsJniBindings.my_bam_get_mate_alignment_start(b) ;
			}
		@Override
		public int getFlags() {
			return HtsJniBindings.my_bam_get_flags(b);
			}
		
		@Override
		public int getReadLength() {
			return HtsJniBindings.my_bam_get_read_length(b);
			}
		
		@Override
		public char getReadBaseAt(int index) {
			return (char)HtsJniBindings.my_bam_get_read_base_at(b,index);
			}
		@Override
		public int getReadQualAt(int index) {
			return HtsJniBindings.my_bam_get_read_qual_at(b,index);
			}
		@Override
		public String getReadString()
			{
			int L=this.getReadLength();
			StringBuilder sb=new StringBuilder(L);
			for(int i=0;i< L;++i)
				{
				sb.append(this.getReadBaseAt(i));
				}
			return sb.toString();
			}
		@Override
		public int getInferredInsertSize()
			{
			return HtsJniBindings.my_bam_get_inferred_size(b);
			}
		@Override
		public String getBaseQualityString()
			{
			int L=this.getReadLength();
			StringBuilder sb=new StringBuilder(L);
			for(int i=0;i< L;++i)
				{
				sb.append((char)(33+getReadQualAt(i)));
				}
			return sb.toString();
			}
		@Override
		public int getMappingQuality() {
			if(!isReadMapped()) return 0;
			return HtsJniBindings.my_bam_get_mapping_quality(b);
			}
		@Override
		public int getCigarNumElements()
			{
			if(!isReadMapped()) return 0;
			return HtsJniBindings.my_bam_get_cigar_num_elements(b);
			}
		@Override
		public char getCigarOperator(int index)
			{
			if(!isReadMapped()) return '\0';
			return (char)HtsJniBindings.my_bam_get_cigar_op(b, index);
			}
		@Override
		public int getCigarLength(int index)
			{
			if(!isReadMapped()) return 0;
			return HtsJniBindings.my_bam_get_cigar_length(b, index);
			}
		@Override
		public String getCigarString()
			{
			int L=getCigarNumElements();
			StringBuilder sb=new StringBuilder(L*3);
			for(int i=0;i< L;++i)
				{
				sb.append(getCigarLength(i));
				sb.append(getCigarOperator(i));
				}
			return sb.toString();
			}
		
		private byte[] getAux()
			{
			return HtsJniBindings.my_bam_get_aux(b);
			}
		
		 
		
		@Override
		public String toString() {
			StringBuilder sb=new StringBuilder();
			sb.append("name:").append(getReadName());
			sb.append(" paired:").append(isPaired());
			sb.append(" mapped:").append(isReadMapped());
			sb.append(" chrom:").append(getReferenceIndex());
			sb.append(" flags:").append(getFlags());
			sb.append(" mapq:").append(getMappingQuality());
			sb.append(" alignment-start:").append(getAlignmentStart());
			sb.append(" length:").append(getReadLength());
			sb.append(" bases:").append(getReadString());
			sb.append(" quals:").append(getBaseQualityString());
			sb.append(" cigar:").append(getCigarString());
			sb.append(" inferred_size:").append(getInferredInsertSize());
			sb.append(" aux:").append(getAux().length);
			if(isPaired())
				{
				if(isMateMapped())
					{
					sb.append(" mate-chrom:").append(getMateReferenceIndex());
					sb.append(" mate-pos:").append(getMateAlignmentStart());
					}
				}
			
 			return sb.toString();
			}
		
		}
	}
