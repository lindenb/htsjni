SHELL=/bin/bash
ifeq (${JAVA_HOME},)
$(error $$JAVA_HOME is not defined)
endif


CFLAGS= -Wall -D_FILE_OFFSET_BITS=64 -D_LARGEFILE_SOURCE -I htslib $(addprefix -I, $(sort $(dir $(shell find "${JAVA_HOME}" -type f -name "*.h"))))

test: src/htslib/HtslibTest.java htslib.jar
	rm -rf tmp
	mkdir -p tmp
	javac -d tmp -cp htslib.jar:. $<
	java -cp htslib.jar:tmp htslib.HtslibTest
	rm -rf tmp

htslib.jar : src/htslib/Htslib.java htslib/libhts.so src/htslib/bindings.c
	rm -rf tmp
	mkdir -p tmp
	javac -d tmp -cp . $<
	javah -cp tmp -o tmp/bindings.h -force htslib.Htslib
	mkdir -p $(dir $@) 
	$(CC) ${CFLAGS} -fPIC -shared -Lhtslib -Wl,-rpath=htslib -o tmp/htslib/libhtsbindings.so  -Itmp src/htslib/bindings.c htslib/libhts.a
	nm tmp/htslib/libhtsbindings.so
	jar cvf htslib.jar -C tmp .
	rm -rf tmp

htslib/libhts.so: htslib/Makefile
	$(MAKE) -C htslib libhts.so

htslib/Makefile:
	rm -rf htslib
	git clone "https://github.com/samtools/htslib" htslib
	touch -c $@

clean:
	rm -rf htslib


