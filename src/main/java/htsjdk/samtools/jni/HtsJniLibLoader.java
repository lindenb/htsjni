package htsjdk.samtools.jni;

class HtsJniLibLoader
{
private static boolean INIT=false;
private HtsJniLibLoader() {}
static void init()
	{
	if(!INIT)
		{
		System.loadLibrary("htsjni");
		}
	INIT=true;
	}
}
