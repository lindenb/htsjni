/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.4
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package htsjdk.samtools.jni;

public class CWrapper {
  private long swigCPtr;

  public CWrapper(long cPtr) {
	   this(cPtr,false);
	  }
  
  public CWrapper(long cPtr, boolean futureUse) {
    swigCPtr = cPtr;
  }

  public CWrapper() {
    this(0L);
  }

  public long getCPtr()
  	{
	return this.swigCPtr;
  	}
  
  public boolean isNull()
  	{
	  return this.swigCPtr==0L;
  	}
  public void free()
  	{
	this.swigCPtr=0L;  
  	}
  
  @Override
	public String toString() {
		return getClass().getName()+" Ptr="+this.swigCPtr;
		}
  
  protected static long getCPtr(CWrapper obj) {
    return (obj == null) ? 0L : obj.swigCPtr;
  }
}
