SHELL=/bin/bash
ifeq (${JAVA_HOME},)
$(error $$JAVA_HOME is not defined)
endif

HTSOBJ=kfunc knetfile kstring bcf_sr_sort bgzf errmod faidx header hfile hfile_net hts  multipart probaln realn regidx region sam synced_bcf_reader vcf_sweep tbx textutils thread_pool vcf vcfutils cram/cram_codecs cram/cram_decode cram/cram_encode cram/cram_external cram/cram_index cram/cram_io cram/cram_samtools cram/cram_stats cram/mFILE cram/open_trace_file cram/pooled_alloc cram/rANS_static cram/string_alloc

CFLAGS= -Wall -D_FILE_OFFSET_BITS=64 -D_LARGEFILE_SOURCE -I htslib $(addprefix -I, $(sort $(dir $(shell find "${JAVA_HOME}" -type f -name "*.h"))))

test: src/htslib/HtslibTest.java htslib.jar
	rm -rf tmp
	mkdir -p tmp
	javac -d tmp -cp htslib.jar:. $<
	java -cp htslib.jar:tmp htslib.HtslibTest
	rm -rf tmp

htslib.jar : src/htslib/Htslib.java htslib/libhts.a src/htslib/bindings.c
	rm -rf tmp
	mkdir -p tmp
	javac -d tmp -cp . $<
	javah -cp tmp -o src/htslib/bindings.h -force htslib.Htslib
	mkdir -p $(dir $@) 
	$(CC) ${CFLAGS} -fPIC -shared  -o tmp/htslib/libhtsbindings.so  -Isrc src/htslib/bindings.c htslib/libhts.a  -lz -lm -lbz2 -llzma -lcurl
	#nm tmp/htslib/libhtsbindings.so
	jar cvf htslib.jar -C tmp .
	rm -rf tmp

htslib/libhts.a: htslib/Makefile
	$(MAKE) -C htslib libhts.a

htslib/Makefile:
	rm -rf htslib
	git clone "https://github.com/samtools/htslib" htslib
	cp $@ $(addsuffix .back,$@)
	sed -i '/^CFLAGS[ ]*=/s/$$/ -fPIC /' $@
	touch -c $@

clean:
	rm -rf htslib htslib.jar tmp


