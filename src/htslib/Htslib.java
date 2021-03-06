/*
The MIT License (MIT)

Copyright (c) 2020 Pierre Lindenbaum

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

*/
package htslib;

import java.io.*;
import java.net.*;
import java.util.*;

public class Htslib {
    private static boolean inited = false;
    //
    public static native String getVersion();

	
    public static void init() throws IOException {
         if(Htslib.inited) return;
         Htslib.inited = true;
         final String RSRC_NAME="htslib/libhtsbindings.so";
         final Enumeration<URL> eu = Htslib.class.getClassLoader().getResources(RSRC_NAME);
         while (eu.hasMoreElements()) {
            final URL url = eu.nextElement();
            try(InputStream in= url.openStream()) {
                if(in==null) continue;
                final File tmp = File.createTempFile("lib",".so");
                tmp.deleteOnExit();
                try(FileOutputStream os = new FileOutputStream(tmp)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                        }
                    os.flush();
                    os.close();
                    System.load(tmp.getAbsolutePath());
                    return;
                    }
                }
            }
        throw new IOException("Resource not found: " + RSRC_NAME );
        }
   	//kstring
   	public static native long hts_ks_new();
   	public static native int hts_ks_len(long s);
    public static native String hts_ks_str(long s);  
    
	// io
	public static native long hts_hopen(final String filename,final String mode);
	public static native void hts_hclose(long fp);

	//vcf header
	public static native long hts_bcf_hdr_read(long fp);
	public static native void hts_bcf_hdr_destroy(long header);
	public static native int hts_bcf_hdr_nsamples(long header);
	public static native String[] hts_bcf_hdr_get_samples(long header);


	//bcf record
	public static native long hts_bcf_init();
	public static native void hts_bcf_destroy(long ptr);
	public static native int hts_bcf_read(long fp,long header, long v);
	public static native long hts_bcf_copy(long dst, long src);
	public static native long hts_bcf_dup(long src);

}

