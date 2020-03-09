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
#include "version.h"

#include "bindings.h"

#ifdef JNIEXPORT
    #undef JNIEXPORT
#endif
#ifdef JNICALL
    #undef JNICALL
#endif


#define JNIEXPORT
#define JNICALL

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



jstring JNICALL Java_htslib_Htslib_getVersion(JNIEnv *env, jclass clazz) {
    return (*env)->NewStringUTF(env,HTS_VERSION_TEXT);
    }



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


// BCF HEADER

jlong METHOD(1bcf_1hdr_1read)(JNIEnv *env, jclass c, jlong fp) {
    if(fp==0L) return 0L;
    return (jlong)bcf_hdr_read((htsFile*)fp);
    }


void METHOD(1bcf_1hdr_1destroy)(JNIEnv *env, jclass c, jlong header) {
    if(header>0L) bcf_hdr_destroy((bcf_hdr_t*)header);
    }

// BCF_T

/*
 * Class:     htslib_Htslib
 * Method:    hts_bcf_init
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL METHOD(1bcf_1init)(JNIEnv *e, jclass c) {
    return (jlong)bcf_init();
    }

/*
 * Class:     htslib_Htslib
 * Method:    hts_bcf_destroy
 * Signature: (J)V
 */
JNIEXPORT void JNICALL METHOD(1bcf_1destroy)(JNIEnv *e, jclass c, jlong b) {
    if(b>0L) bcf_destroy((bcf1_t*)b);
    }


/*
 * Class:     htslib_Htslib
 * Method:    hts_bcf_read1_core
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_htslib_Htslib_hts_1bcf_1read  (JNIEnv * e, jclass c, jlong fp, jlong header, jlong b) {
return (jint)bcf_read((htsFile*)fp,(bcf_hdr_t*)header,(bcf1_t*)b);
}

/*
 * Class:     htslib_Htslib
 * Method:    hts_bcf_copy
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_htslib_Htslib_hts_1bcf_1copy  (JNIEnv *e, jclass c, jlong dest, jlong src) {
return (jlong)bcf_copy((bcf1_t*)dest,(bcf1_t*)src);
}

/*
 * Class:     htslib_Htslib
 * Method:    hts_bcf_dup
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_htslib_Htslib_hts_1bcf_1dup (JNIEnv *e , jclass c , jlong b) {
return b>0L?(jlong)bcf_dup((bcf1_t*)b):(jlong)0L;
}

