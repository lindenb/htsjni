SHELL=/bin/bash
JAVA_HOME=/home/lindenb/package/jdk1.7.0_01
CFLAGS= -Wall -D_FILE_OFFSET_BITS=64 -D_LARGEFILE_SOURCE -I ${LIBHTS} -I ${SAMTOOLS} -I ${JAVA_HOME}/include -I ${JAVA_HOME}/include/linux
LIBHTS=/home/lindenb/package/htslib
SAMTOOLS=/home/lindenb/package/samtools

test: dist/htsjni.jar lib/libhtsjni.so 
	mkdir -p tmp && \
	mkdir -p $(dir $@) && \
	javac -cp  dist/htsjni.jar:../htsjdk/dist/htsjdk-1.114.jar -d tmp -sourcepath src/tests/java src/tests/java/htsjdk/samtools/jni/SamReaderFactoryTest.java && \
	LD_LIBRARY_PATH=lib:${LIBHTS}:${SAMTOOLS} java -XshowSettings:properties -cp dist/htsjni.jar:../htsjdk/dist/htsjdk-1.114.jar:../htsjdk/lib/apache-ant-1.8.2-bzip2.jar:tmp: htsjdk.samtools.jni.SamReaderFactoryTest
	rm -rf tmp

lib/libhtsjni.so : src/main/java/htsjdk/samtools/jni/htsjni.c src/main/java/htsjdk/samtools/jni/htsjni.h
	mkdir -p $(dir $@) && \
	gcc ${CFLAGS} -fpic -shared -o $@  $<  ${SAMTOOLS}/libbam.a -L${LIBHTS} -L${SAMTOOLS} -lm -lz  -lpthread -lhts


src/main/java/htsjdk/samtools/jni/htsjni.h : dist/htsjni.jar
	javah -jni  -classpath $< -o $@  htsjdk.samtools.jni.HtsJniBindingsJNI


dist/htsjni.jar: src/main/java/htsjdk/samtools/jni/htsjni.c
	mkdir -p tmp && \
	mkdir -p $(dir $@) && \
	javac -d tmp -sourcepath src/main/java src/main/java/htsjdk/samtools/jni/SamReaderFactory.java && \
	jar cvf $@ -C tmp . && \
	rm -rf tmp

xxx: src/main/java/htsjdk/samtools/jni/swig/htsjni.c
src/main/java/htsjdk/samtools/jni/swig/htsjni.c : src/main/swig/hts.i
	mkdir -p $(dir $@) && \
	swig -java -package htsjdk.samtools.jni.swig -o $@ $< 

#	-Djava.library.path=/home/lindenb/src/htsjdk/lib/jni:/usr/java/packages/lib/i386:/lib:/usr/lib:${LIBHTS}:${SAMTOOLS} 
#all:lib/jni/libbamreader.so
#	LD_LIBRARY_PATH=/home/lindenb/src/htsjdk/lib/jni:${LIBHTS}:${SAMTOOLS} \
#	java -XshowSettings:properties \
#        -cp bin:lib/apache-ant-1.8.2-bzip2.jar:lib/snappy-java-1.0.3-rc3.jar htsjdk.samtools.NativeBamReader
