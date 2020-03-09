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

import java.util.logging.*;

public class HtslibTest {
    private static final Logger LOG = Logger.getLogger("HtslibTest");

    private void testIO(final String fn) throws Exception {
       long fid = Htslib.hts_hopen(fn,"r");
       Htslib.hts_hclose(fid);
       LOG.info(fn);
    }

    private void testVersion() throws Exception {
       LOG.info(Htslib.getVersion());
    }

    private void testHeader() throws Exception {
       long fid = Htslib.hts_hopen("htslib/test/tabix/vcf_file.bcf","r");
       long header= Htslib.hts_bcf_hdr_read(fid);
       Htslib.hts_bcf_hdr_destroy(header);
       Htslib.hts_hclose(fid);
    }

    private void run() throws Exception {
        Htslib.init();
        testIO("htslib/test/tabix/vcf_file.bcf");
        testVersion();
        testHeader();
    }

    public static void main(final String args[]) {
    try {
        new HtslibTest().run();
        }
    catch(Throwable err)
        {
        err.printStackTrace();
        System.exit(-1);
        }
    }

}

