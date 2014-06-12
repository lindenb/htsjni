htseqjni
========


## Motivation

Native java bindings for htseq using samtools/htslib and samtools/htslib 

Under developpement. 


## Performance ( June 2014) 

in `./src/tests/java/htsjdk/samtools/jni/SamReaderFactoryTest.java`

read 5000x samtools/test/mpileup/mpileup.1.bam, print a MD5 hash of each read[name/bases/quality/pos]

```
Native    (jni)      md5=d41d8cd98f00b204e9800998ecf8427e time=28379 msecs
Pure Java (htsjdk)   md5=d41d8cd98f00b204e9800998ecf8427e time=49878 msecs
```

## Author

Pierre Lindenbaum PhD @yokofakun

