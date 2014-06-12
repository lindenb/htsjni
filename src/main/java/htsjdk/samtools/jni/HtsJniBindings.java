/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.4
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package htsjdk.samtools.jni;


public class HtsJniBindings {
  public static CWrapper_p_bamFile bam_open(String fn, String mode) {
    return new CWrapper_p_bamFile(HtsJniBindingsJNI.bam_open(fn, mode), true);
  }

  public static int bam_close(CWrapper_p_bamFile fp) {
    return HtsJniBindingsJNI.bam_close(CWrapper_p_bamFile.getCPtr(fp));
  }

  public static CWrapper_p_bam_hdr_t bam_hdr_read(CWrapper_p_bamFile fp) {
    long cPtr = HtsJniBindingsJNI.bam_hdr_read(CWrapper_p_bamFile.getCPtr(fp));
    return (cPtr == 0) ? null : new CWrapper_p_bam_hdr_t(cPtr, false);
  }

  public static int bam_read1(CWrapper_p_bamFile fp, CWrapper_p_bam1_t b) {
    return HtsJniBindingsJNI.bam_read1(CWrapper_p_bamFile.getCPtr(fp), CWrapper_p_bam1_t.getCPtr(b));
  }

  public static CWrapper_p_bam1_t bam_init1() {
    long cPtr = HtsJniBindingsJNI.bam_init1();
    return (cPtr == 0) ? null : new CWrapper_p_bam1_t(cPtr, false);
  }

  public static void bam_destroy1(CWrapper_p_bam1_t b) {
    HtsJniBindingsJNI.bam_destroy1(b.getCPtr());
  }

  public static String bam_get_qname(CWrapper_p_bam1_t b) {
    return HtsJniBindingsJNI.bam_get_qname(b.getCPtr());
  }


  public static int my_bam_get_flags(CWrapper_p_bam1_t b) {
    return HtsJniBindingsJNI.my_bam_get_flags(b.getCPtr());
  }

  public static int my_bam_get_alignment_start(CWrapper_p_bam1_t b) {
    return HtsJniBindingsJNI.my_bam_get_alignment_start(b.getCPtr());
  }
  public static int my_bam_get_read_length(CWrapper_p_bam1_t b) {
	    return HtsJniBindingsJNI.my_bam_get_read_length(b.getCPtr());
	  }
  public static int my_bam_get_read_base_at(CWrapper_p_bam1_t b,int index) {
	    return HtsJniBindingsJNI.my_bam_get_read_base_at(b.getCPtr(),index);
	  }

  public static int my_bam_get_read_qual_at(CWrapper_p_bam1_t b,int index) {
	    return HtsJniBindingsJNI.my_bam_get_read_qual_at(b.getCPtr(),index);
	  }
  public static int my_bam_get_reference_index(CWrapper_p_bam1_t b) {
	    return HtsJniBindingsJNI.my_bam_get_reference_index(b.getCPtr());
	  }
  public static int my_bam_get_mapping_quality(CWrapper_p_bam1_t b) {
	    return HtsJniBindingsJNI.my_bam_get_mapping_quality(b.getCPtr());
	  }
  public static int my_bam_get_mate_reference_index(CWrapper_p_bam1_t b) {
	    return HtsJniBindingsJNI.my_bam_get_mate_reference_index(b.getCPtr());
	  }
  public static int my_bam_get_mate_alignment_start(CWrapper_p_bam1_t b) {
	    return HtsJniBindingsJNI.my_bam_get_mate_alignment_start(b.getCPtr());
	  }
  
  public static int my_bam_get_cigar_num_elements(CWrapper_p_bam1_t b) {
	    return HtsJniBindingsJNI.my_bam_get_cigar_num_elements(b.getCPtr());
	  }
  
  public static int my_bam_get_cigar_op(CWrapper_p_bam1_t b,int index) {
	    return HtsJniBindingsJNI.my_bam_get_cigar_op(CWrapper_p_bam1_t.getCPtr(b),index);
	  }

  public static int my_bam_get_cigar_length(CWrapper_p_bam1_t b,int index) {
	    return HtsJniBindingsJNI.my_bam_get_cigar_length(CWrapper_p_bam1_t.getCPtr(b),index);
	  }

  public static int my_bam_get_inferred_size(CWrapper_p_bam1_t b) {
	    return HtsJniBindingsJNI.my_bam_get_inferred_size(CWrapper_p_bam1_t.getCPtr(b));
	  }

  
  public static int bam_hdr_count_references(CWrapper_p_bam_hdr_t b) {
	    return HtsJniBindingsJNI.bam_hdr_count_references(b.getCPtr());
	  }

  public static String bam_hdr_get_reference_name(CWrapper_p_bam_hdr_t b,int index) {
	    return HtsJniBindingsJNI.bam_hdr_get_reference_name(b.getCPtr(),index);
	  }
  public static int bam_hdr_get_reference_length(CWrapper_p_bam_hdr_t b,int index) {
	    return HtsJniBindingsJNI.bam_hdr_get_reference_length(b.getCPtr(),index);
	  }
  
  public static byte[] my_bam_get_aux(CWrapper_p_bam1_t b) {
	    return HtsJniBindingsJNI.my_bam_get_aux(b.getCPtr());
	  }

  
}
