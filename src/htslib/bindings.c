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
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <htslib/kstring.h>
#include <htslib/vcf.h>
#include <htslib/hts.h>
#include "bindings.h"

/*
static void* safeMalloc(size_t size) {
	void* ptr = malloc(size);
	if(ptr==NULL) {
		fprintf(stderr,"cannot alloc memory.\n");
		exit(EXIT_FAILURE);
		}
	memset(ptr,0,size);
	return ptr;
	}*/

#define C_VAR(S) _c_ ## S

#define STR_J2C(S) const char* C_VAR(S) = ( const char *) (*env)->GetStringUTFChars(env, S, NULL)
#define FREE_STR(S) (*env)->ReleaseStringUTFChars(env,S,C_VAR(S))



#define METHOD(NAME) Java_htslib_Htslib_hts_##NAME 

jlong METHOD(1hopen) (JNIEnv *env, jclass clazz, jstring filename, jstring mode) {
    htsFile* f;
	STR_J2C(filename);
	STR_J2C(mode);
	f = hts_open(C_VAR(filename),C_VAR(mode));
	FREE_STR(filename);
	FREE_STR(mode);
	return (jlong)f;
	}


void METHOD(1hclose) (JNIEnv* env, jclass c, jlong ptr) {
	if(ptr<=0L) return;
	hts_close((htsFile*)ptr);
	}

